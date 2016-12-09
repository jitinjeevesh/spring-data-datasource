package com.spring.datasource.core;

import com.google.cloud.datastore.*;
import com.google.cloud.datastore.Query;
import com.spring.datasource.core.convert.DataStoreConverter;
import com.spring.datasource.core.query.Criteria;
import com.spring.datasource.core.query.DataStoreQuery;
import com.spring.datasource.core.query.DynamicQuery;
import com.spring.datasource.core.query.StringQuery;
import com.spring.datasource.mapping.DataStorePersistentEntity;
import com.spring.datasource.mapping.DataStorePersistentProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.util.Assert;
import com.spring.datasource.repository.query.PartTreeDataStoreQuery;
import com.spring.datasource.repository.query.StringBasedDataStoreQuery;

import java.util.*;

public class DataStoreTemplate implements DataStoreOperation {

    private static final Logger LOG = LoggerFactory.getLogger(DataStoreTemplate.class);

    private static final String DATASTORE_MUST_NOT_BE_NULL = "Data store object must not be null";

    private final DataStoreConverter dataStoreConverter;
    private final MappingContext<? extends DataStorePersistentEntity<?>, DataStorePersistentProperty> mappingContext;
    private Datastore datastore;

    public DataStoreTemplate(DataStoreFactory datastore, DataStoreConverter dataStoreConverter) {
        Assert.notNull(datastore, DATASTORE_MUST_NOT_BE_NULL);
        this.datastore = datastore.getDefaultDataStore();
        this.dataStoreConverter = dataStoreConverter;
        this.mappingContext = this.dataStoreConverter.getMappingContext();
    }


    @Override
    public <E> void insert(Object objectToSave, Class<E> entityClass) {
        //TODO:Move this entity class.
        doInsert(determineCollectionName(entityClass), objectToSave);
    }

    //TODO:String type of key generation is not implemented yet.
    protected <E> void doInsert(String kindName, E objectToSave) {
        KeyFactory keyFactory = datastore.newKeyFactory().setKind(kindName);
        Key key = datastore.allocateId(keyFactory.newKey());
        FullEntity<?> nativeEntity = (FullEntity<?>) dataStoreConverter.convertToDataStoreType(objectToSave, key);
        datastore.add(nativeEntity);
    }

    @Override
    public <E> void save(Object objectToSave, Class<E> entityClass) {
        KeyFactory keyFactory = datastore.newKeyFactory().setKind(determineCollectionName(entityClass));
        Entity nativeEntity = (Entity) dataStoreConverter.convertToDataStoreType(objectToSave, keyFactory);
        datastore.put(nativeEntity);
    }

    private DataStorePersistentEntity<?> getPersistentEntity(Class<?> type) {
        return type == null ? null : mappingContext.getPersistentEntity(type);
    }

    //TODO:Throw custom errors.
    @Override
    public <E> E load(Class<E> entityClass, String id) {
        try {
            KeyFactory keyFactory = datastore.newKeyFactory().setKind(determineCollectionName(entityClass));
            Key key = keyFactory.newKey(id);
            Entity entity = datastore.get(key);
            E convertedEntity = dataStoreConverter.read(entityClass, entity);
            return convertedEntity;
        } catch (DatastoreException exp) {
            exp.printStackTrace();
        }
        return null;
    }

    @Override
    public <E> E load(Class<E> entityClass, long id) {
        try {
            KeyFactory keyFactory = datastore.newKeyFactory().setKind(determineCollectionName(entityClass));
            Key key = keyFactory.newKey(id);
            Entity entity = datastore.get(key);
            E convertedEntity = dataStoreConverter.read(entityClass, entity);
            return convertedEntity;
        } catch (DatastoreException exp) {
            exp.printStackTrace();
        }
        return null;
    }

    //TODO:Refactor this code.
    public <E> List<E> findAll(Class<E> entityClass, Integer limit) {
        List<E> entities = new ArrayList<>();
        try {
            boolean allRecords = false;
            Long totalCount = count(entityClass);
            String baseQuery = "SELECT * FROM " + determineCollectionName(entityClass) + " LIMIT @Limit";
            Map<String, Object> binding = new HashMap<String, Object>();
            Date start = new Date();
            if (totalCount < 500) {
                binding.put("Limit", totalCount);
                allRecords = true;
            } else {
                binding.put("Limit", limit);
            }
            GqlQuery.Builder<Entity> queryBuilder = Query.newGqlQueryBuilder(Query.ResultType.ENTITY, baseQuery);
            applyNamedBindings(queryBuilder, binding);
            queryBuilder.setAllowLiteral(false);
            GqlQuery<Entity> gqlQuery = queryBuilder.build();
            QueryResults<Entity> results = datastore.run(gqlQuery);
            DataStoreQueryResponseImpl<E> dataStoreQueryResponse = new DataStoreQueryResponseImpl<E>();
            dataStoreQueryResponse.setStartCursor(results.getCursorAfter().toUrlSafe());
            while (results.hasNext()) {
                Entity result = results.next();
                E convertedEntity = dataStoreConverter.read(entityClass, result);
                entities.add(convertedEntity);
            }
            Date end = new Date();
            dataStoreQueryResponse.setResults(entities);
            dataStoreQueryResponse.setEndCursor(results.getCursorAfter().toUrlSafe());
            List<E> tempList = new ArrayList<>();
            if (!allRecords) {
                do {
                    tempList.clear();
                    String query = baseQuery + " OFFSET @Offset";
                    GqlQuery.Builder<Entity> queryBuilder1 = Query.newGqlQueryBuilder(Query.ResultType.ENTITY, query);
                    queryBuilder1.setBinding("Limit", limit);
                    queryBuilder1.setBinding("Offset", results.getCursorAfter());
                    GqlQuery<Entity> gqlQuery1 = queryBuilder1.build();
                    results = datastore.run(gqlQuery1);
                    while (results.hasNext()) {
                        Entity result = results.next();
                        E convertedEntity = dataStoreConverter.read(entityClass, result);
                        entities.add(convertedEntity);
                        tempList.add(convertedEntity);
                    }
                    Date end1 = new Date();
                } while (!tempList.isEmpty());
            }
        } catch (DatastoreException exp) {
            exp.printStackTrace();
        }
        return entities;
    }

    @Override
    public <E> List<E> findAll(Class<E> entityClass) {
        return findAll(entityClass, 500);
    }

    //TODO:Sort is not implemented yet.
    public <E> List<E> findAll(Class<E> entityClass, Pageable pageable) {
        List<E> entities = new ArrayList<>();
        Query<Entity> query = Query.newEntityQueryBuilder().setKind(determineCollectionName(entityClass)).setLimit(pageable.getPageSize()).setOffset(pageable.getOffset()).build();
        QueryResults<Entity> results = datastore.run(query);
        while (results.hasNext()) {
            Entity result = results.next();
            E convertedEntity = dataStoreConverter.read(entityClass, result);
            entities.add(convertedEntity);
        }
        return entities;
    }

    @Override
    public <E> List<E> findAll(DataStoreQuery dataStoreQuery, Pageable pageable, Class<E> type, String kindName) {
        if (dataStoreQuery instanceof DynamicQuery) {
            return doFind(((DynamicQuery) dataStoreQuery).with(pageable), type, kindName);
        } else if (dataStoreQuery instanceof StringQuery) {
            return doFind(((StringQuery) dataStoreQuery), type, kindName);
        } else {
            throw new InvalidDataAccessApiUsageException("No other query method defined");
        }
    }

    private <E> List<E> doFind(StringQuery dynamicQuery, Class<E> type, String kindName) {
        int limit = 1000;
        List<E> entities = new ArrayList<>();
        try {
            String baseQuery = dynamicQuery.getOriginalQuery() + " LIMIT @Limit";
            Map<String, Object> binding = dynamicQuery.getCriteria();
            binding.put("Limit", limit);
            System.out.println(binding);
            GqlQuery.Builder<Entity> queryBuilder = Query.newGqlQueryBuilder(Query.ResultType.ENTITY, baseQuery);
            applyNamedBindings(queryBuilder, binding);
            queryBuilder.setAllowLiteral(false);
            GqlQuery<Entity> gqlQuery = queryBuilder.build();
            System.out.println(gqlQuery);
            QueryResults<Entity> results = datastore.run(gqlQuery);
            DataStoreQueryResponseImpl<E> dataStoreQueryResponse = new DataStoreQueryResponseImpl<E>();
            dataStoreQueryResponse.setStartCursor(results.getCursorAfter().toUrlSafe());
            while (results.hasNext()) {
                Entity result = results.next();
                E convertedEntity = dataStoreConverter.read(type, result);
                entities.add(convertedEntity);
            }
            dataStoreQueryResponse.setResults(entities);
            dataStoreQueryResponse.setEndCursor(results.getCursorAfter().toUrlSafe());
            List<E> tempList = new ArrayList<>();
            do {
                tempList.clear();
                String query = baseQuery + " OFFSET @Offset";
                GqlQuery.Builder<Entity> queryBuilder1 = Query.newGqlQueryBuilder(Query.ResultType.ENTITY, query);
                binding.put("Offset", results.getCursorAfter());
                applyNamedBindings(queryBuilder1, binding);
                GqlQuery<Entity> gqlQuery1 = queryBuilder1.build();
                results = datastore.run(gqlQuery1);
                while (results.hasNext()) {
                    Entity result = results.next();
                    E convertedEntity = dataStoreConverter.read(type, result);
                    entities.add(convertedEntity);
                    tempList.add(convertedEntity);
                }
            } while (!tempList.isEmpty());
        } catch (DatastoreException exp) {
            exp.printStackTrace();
        }
        return entities;
    }

    private <E> List<E> doFind(DynamicQuery dynamicQuery, Class<E> type, String kindName) {
        int limit = dynamicQuery.getLimit() < 0 ? dynamicQuery.getLimit() : 5000;
        int offset = dynamicQuery.getOffset() < 0 ? dynamicQuery.getOffset() : 0;
        List<Criteria.ParameterBinding> parameterBindings = dynamicQuery.getParameterBindings();
        StringBuilder stringBuffer = new StringBuilder("SELECT * FROM " + kindName + " where ");
        for (Criteria.ParameterBinding parameterBinding : parameterBindings) {
            stringBuffer.append(parameterBinding.getName()).append(" = ").append("@").append(parameterBinding.getName()).append(" AND ");
        }
        String stringQuery = stringBuffer.toString();
        String finalQuery = stringQuery.substring(0, stringQuery.lastIndexOf("AND "));
        List<E> entities = new ArrayList<>();
        try {
            String baseQuery = finalQuery + " LIMIT @Limit";
            Map<String, Object> binding = new HashMap<String, Object>();
            binding.put("Limit", limit);
            for (Criteria.ParameterBinding parameterBinding : parameterBindings) {
                binding.put(parameterBinding.getName(), parameterBinding.getValue());
            }
            GqlQuery.Builder<Entity> queryBuilder = Query.newGqlQueryBuilder(Query.ResultType.ENTITY, baseQuery);
            applyNamedBindings(queryBuilder, binding);
            queryBuilder.setAllowLiteral(false);
            GqlQuery<Entity> gqlQuery = queryBuilder.build();
            QueryResults<Entity> results = datastore.run(gqlQuery);
            DataStoreQueryResponseImpl<E> dataStoreQueryResponse = new DataStoreQueryResponseImpl<E>();
            dataStoreQueryResponse.setStartCursor(results.getCursorAfter().toUrlSafe());
            while (results.hasNext()) {
                Entity result = results.next();
                E convertedEntity = dataStoreConverter.read(type, result);
                entities.add(convertedEntity);
            }
            dataStoreQueryResponse.setResults(entities);
            dataStoreQueryResponse.setEndCursor(results.getCursorAfter().toUrlSafe());
            List<E> tempList = new ArrayList<>();
            do {
                tempList.clear();
                String query = baseQuery + " OFFSET @Offset";
                GqlQuery.Builder<Entity> queryBuilder1 = Query.newGqlQueryBuilder(Query.ResultType.ENTITY, query);
                binding.put("Offset", results.getCursorAfter());
                applyNamedBindings(queryBuilder1, binding);
                GqlQuery<Entity> gqlQuery1 = queryBuilder1.build();
                results = datastore.run(gqlQuery1);
                while (results.hasNext()) {
                    Entity result = results.next();
                    E convertedEntity = dataStoreConverter.read(type, result);
                    entities.add(convertedEntity);
                    tempList.add(convertedEntity);
                }
            } while (!tempList.isEmpty());
        } catch (DatastoreException exp) {
            exp.printStackTrace();
        }
        return entities;
    }

    @Override
    public long count(Class<?> entityClass) {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("__Stat_Kind__").setFilter(StructuredQuery.PropertyFilter.eq("kind_name", determineCollectionName(entityClass)))
                .build();
        QueryResults<Entity> results = datastore.run(query);
        long count = 0;
        while (results.hasNext()) {
            Entity newEntity = results.next();
            count = newEntity.getLong(DataStoreConstants.TOTAL_COUNT_PROPERTY_NAME);
        }
        return count;
    }

    @Override
    public long count(DataStoreQuery dynamicQuery, Class<?> type, String kindName) {
        return 0;
    }

    @Override
    public <E> E findOne(DataStoreQuery dataStoreQuery, Class<E> type, String kindName) {
        if (dataStoreQuery instanceof DynamicQuery) {
            return doFindOne((DynamicQuery) dataStoreQuery, type, kindName);
        } else if (dataStoreQuery instanceof StringQuery) {
            return doFindOne((StringQuery) dataStoreQuery, type);
        } else {
            throw new InvalidDataAccessApiUsageException("No other query method defined");
        }
    }

    /**
     * This method is used to execute {@link StringBasedDataStoreQuery} value.
     *
     * @param stringQuery  contains the GQL query.
     * @param type         Datastore Entity mapping Java type
     * @param <E>Converter java type
     * @return Converted Java Object
     */
    private <E> E doFindOne(StringQuery stringQuery, Class<E> type) {
        Query<Entity> query = Query.newGqlQueryBuilder(Query.ResultType.ENTITY, stringQuery.getQuery()).setAllowLiteral(true).build();
        QueryResults<Entity> results = datastore.run(query);
        E convertedEntity = null;
        if (results.hasNext()) {
            Entity newEntity = results.next();
            convertedEntity = dataStoreConverter.read(type, newEntity);
        }
        return convertedEntity;
    }

    /**
     * This method is used to execute the Dynamic queries {@link PartTreeDataStoreQuery} value.
     *
     * @param dynamicQuery contains the dynamic query.
     * @param type         Datastore Entity mapping Java type
     * @param <E>Converter java type
     * @param kindName     Datastore kind name
     * @return Converted Java Object
     */
    private <E> E doFindOne(DynamicQuery dynamicQuery, Class<E> type, String kindName) {
        //TODO:Only one object will be fetched for now.
        Query<Entity> query = Query.newEntityQueryBuilder().setKind(kindName).setFilter(dynamicQuery.getPropertyFilter()).build();
        QueryResults<Entity> results = datastore.run(query);
        E convertedEntity = null;
        if (results.hasNext()) {
            Entity newEntity = results.next();
            convertedEntity = dataStoreConverter.read(type, newEntity);
        }
        return convertedEntity;
    }

    @Override
    public <E> E update(DataStoreQuery dataStoreQuery, Class<E> type, String kindName) {
        if (dataStoreQuery instanceof DynamicQuery) {
            return doUpdate((DynamicQuery) dataStoreQuery, type, kindName);
        } else {
            throw new InvalidDataAccessApiUsageException("No other query method defined");
        }
    }

    public <E> E doUpdate(DynamicQuery dataStoreQuery, Class<E> type, String kindName) {
        KeyFactory keyFactory = datastore.newKeyFactory().setKind(kindName);
        Key key = dataStoreConverter.getKey(dataStoreQuery.getIdParameterBinding().getValue(), keyFactory);
        Entity entity = datastore.get(key);
        Entity nativeEntity = (Entity) dataStoreConverter.convertToDataStoreType(dataStoreQuery, key, entity);
        datastore.put(nativeEntity);
        return null;
    }

    String determineCollectionName(Class<?> entityClass) {
        if (entityClass == null) {
            throw new InvalidDataAccessApiUsageException("No class parameter provided, entity collection can't be determined!");
        }
        DataStorePersistentEntity<?> entity = mappingContext.getPersistentEntity(entityClass);
        if (entity == null) {
            throw new InvalidDataAccessApiUsageException("No Persistent Entity information found for the class " + entityClass.getName());
        }
        return entity.getKind();
    }

    private void applyNamedBindings(GqlQuery.Builder<?> queryBuilder, Map<String, Object> namedBindings) {
        if (namedBindings != null) {
            for (String bindingName : namedBindings.keySet()) {
                Object bindingValue = namedBindings.get(bindingName);
                if (bindingName.contains("@")) {
                    bindingName = bindingName.replace("@", "");
                }
                if (bindingValue instanceof Short) {
                    queryBuilder.setBinding(bindingName, (short) bindingValue);
                } else if (bindingValue instanceof Integer) {
                    queryBuilder.setBinding(bindingName, (int) bindingValue);
                } else if (bindingValue instanceof Long) {
                    queryBuilder.setBinding(bindingName, (long) bindingValue);
                } else if (bindingValue instanceof Float) {
                    queryBuilder.setBinding(bindingName, (float) bindingValue);
                } else if (bindingValue instanceof Double) {
                    queryBuilder.setBinding(bindingName, (double) bindingValue);
                } else if (bindingValue instanceof Boolean) {
                    queryBuilder.setBinding(bindingName, (boolean) bindingValue);
                } else if (boolean.class.isAssignableFrom(bindingValue.getClass())) {
                    queryBuilder.setBinding(bindingName, (boolean) bindingValue);
                } else if (bindingValue instanceof String) {
                    if (((String) bindingValue).contains("'")) {
                        bindingValue = ((String) bindingValue).replace("'", "");
                    }
                    queryBuilder.setBinding(bindingName, (String) bindingValue);
                } else if (bindingValue instanceof Calendar) {
                    queryBuilder.setBinding(bindingName, DateTime.copyFrom((Calendar) bindingValue));
                } else if (bindingValue instanceof Date) {
                    queryBuilder.setBinding(bindingName, DateTime.copyFrom((Date) bindingValue));
                } else if (bindingValue instanceof byte[]) {
                    queryBuilder.setBinding(bindingName, Blob.copyFrom((byte[]) bindingValue));
                } else if (bindingValue instanceof Cursor) {
                    queryBuilder.setBinding(bindingName, (Cursor) bindingValue);
                }
                //TODO:THis is used when cursor is used.
                /* else if (bindingValue instanceof DatastoreCursor) {
                    queryBuilder.setBinding(bindingName,
                            Cursor.fromUrlSafe(((DatastoreCursor) bindingValue).getEncoded()));
                }*/
            }
        }
    }

    @Override
    public DataStoreConverter getConverter() {
        return this.dataStoreConverter;
    }
}
