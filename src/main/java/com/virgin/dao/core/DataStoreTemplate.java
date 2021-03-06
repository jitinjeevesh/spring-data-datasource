package com.virgin.dao.core;

import com.google.cloud.datastore.*;
import com.google.cloud.datastore.Query;
import com.virgin.dao.core.convert.DataStoreConverter;
import com.virgin.dao.core.query.*;
import com.virgin.dao.mapping.DataStorePersistentEntity;
import com.virgin.dao.mapping.DataStorePersistentProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.util.Assert;

import java.util.*;

public class DataStoreTemplate implements DataStoreOperation {

    private static final Logger LOG = LoggerFactory.getLogger(DataStoreTemplate.class);

    private static final String DATASTORE_MUST_NOT_BE_NULL = "Data store object must not be null";

    private final DataStoreConverter dataStoreConverter;
    private final MappingContext<? extends DataStorePersistentEntity<?>, DataStorePersistentProperty> mappingContext;
    private Datastore datastore;

    public DataStoreTemplate(Datastore datastore, DataStoreConverter dataStoreConverter) {
        Assert.notNull(datastore, DATASTORE_MUST_NOT_BE_NULL);
        this.datastore = datastore;
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
    public <E> List<E> findAll(Class<E> entityClass) {
        List<E> entities = new ArrayList<>();
        try {
            boolean allRecords = false;
            Long totalCount = count(entityClass);
            String baseQuery = "SELECT * FROM " + determineCollectionName(entityClass) + " LIMIT @Limit";
            Map<String, Object> binding = new HashMap<String, Object>();
            if (totalCount < 500) {
                binding.put("Limit", totalCount);
                allRecords = true;
            } else {
                binding.put("Limit", 500);
            }
            GqlQuery.Builder<Entity> queryBuilder = Query.gqlQueryBuilder(Query.ResultType.ENTITY, baseQuery);
            applyNamedBindings(queryBuilder, binding);
            queryBuilder.allowLiteral(false);
            GqlQuery<Entity> gqlQuery = queryBuilder.build();
            QueryResults<Entity> results = datastore.run(gqlQuery);
            DataStoreQueryResponseImpl<E> dataStoreQueryResponse = new DataStoreQueryResponseImpl<E>();
            dataStoreQueryResponse.setStartCursor(results.cursorAfter().toUrlSafe());
            while (results.hasNext()) {
                Entity result = results.next();
                E convertedEntity = dataStoreConverter.read(entityClass, result);
                entities.add(convertedEntity);
            }
            dataStoreQueryResponse.setResults(entities);
            dataStoreQueryResponse.setEndCursor(results.cursorAfter().toUrlSafe());
            List<E> tempList = new ArrayList<>();
            if (!allRecords) {
                do {
                    tempList.clear();
                    String query = baseQuery + " OFFSET @Offset";
                    GqlQuery.Builder<Entity> queryBuilder1 = Query.gqlQueryBuilder(Query.ResultType.ENTITY, query);
                    queryBuilder1.setBinding("Limit", 500);
                    queryBuilder1.setBinding("Offset", results.cursorAfter());
                    GqlQuery<Entity> gqlQuery1 = queryBuilder1.build();
                    results = datastore.run(gqlQuery1);
                    while (results.hasNext()) {
                        Entity result = results.next();
                        E convertedEntity = dataStoreConverter.read(entityClass, result);
                        entities.add(convertedEntity);
                        tempList.add(convertedEntity);
                    }
                } while (!tempList.isEmpty());
            }
        } catch (DatastoreException exp) {
            exp.printStackTrace();
        }
        return entities;
    }

    //TODO:Sort is not implemented yet.
    public <E> List<E> findAll(Class<E> entityClass, Pageable pageable) {
        List<E> entities = new ArrayList<>();
        Query<Entity> query = Query.entityQueryBuilder().kind(determineCollectionName(entityClass)).limit(pageable.getPageSize()).offset(pageable.getOffset()).build();
        QueryResults<Entity> results = datastore.run(query);
        while (results.hasNext()) {
            Entity result = results.next();
            E convertedEntity = dataStoreConverter.read(entityClass, result);
            entities.add(convertedEntity);
        }
        return entities;
    }

    @Override
    public long count(Class<?> entityClass) {
        Query<Entity> query = Query.entityQueryBuilder()
                .kind("__Stat_Kind__").filter(StructuredQuery.PropertyFilter.eq("kind_name", determineCollectionName(entityClass)))
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
    public long count(DataStoreQuery dataStoreQuery, Class<?> type, String kindName) {
        return 0;
    }

    @Override
    public <E> E findOne(DataStoreQuery dataStoreQuery, Class<E> type, String kindName) {
        //TODO:Only one object will be fetched for now.
        Query<Entity> query = Query.newEntityQueryBuilder().setKind(kindName).setFilter(dataStoreQuery.getPropertyFilter()).build();
        QueryResults<Entity> results = datastore.run(query);
        E convertedEntity = null;
        if (results.hasNext()) {
            Entity newEntity = results.next();
            convertedEntity = dataStoreConverter.read(type, newEntity);
        }
        return convertedEntity;
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
                } else if (bindingValue instanceof String) {
                    queryBuilder.setBinding(bindingName, (String) bindingValue);
                } else if (bindingValue instanceof Calendar) {
                    queryBuilder.setBinding(bindingName, DateTime.copyFrom((Calendar) bindingValue));
                } else if (bindingValue instanceof Date) {
                    queryBuilder.setBinding(bindingName, DateTime.copyFrom((Date) bindingValue));
                } else if (bindingValue instanceof byte[]) {
                    queryBuilder.setBinding(bindingName, Blob.copyFrom((byte[]) bindingValue));
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
