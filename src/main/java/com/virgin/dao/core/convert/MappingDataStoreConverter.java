package com.virgin.dao.core.convert;

import com.google.cloud.datastore.Entity;
import com.virgin.dao.mapping.DataStorePersistentEntity;
import com.virgin.dao.mapping.DataStorePersistentProperty;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;

public class MappingDataStoreConverter extends AbstractDataStoreConverter {


    protected final MappingContext<? extends DataStorePersistentEntity<?>, DataStorePersistentProperty> mappingContext;
    protected DataStoreTypeMapper dataStoreTypeMapper;

    public MappingDataStoreConverter(MappingContext<? extends DataStorePersistentEntity<?>, DataStorePersistentProperty> mappingContext) {
        this.mappingContext = mappingContext;
        this.dataStoreTypeMapper = new DataStoreTypeMapperImpl(DataStoreTypeMapperImpl.DEFAULT_TYPE_KEY, mappingContext);
    }

    @Override
    public Object convertToDataStoreType(Object obj) {
        return null;
    }

    @Override
    public Object convertToDataStoreType(Object obj, TypeInformation<?> typeInformation) {
        return null;
    }

    @Override
    public MappingContext<? extends DataStorePersistentEntity<?>, DataStorePersistentProperty> getMappingContext() {
        return mappingContext;
    }

    @Override
    public ConversionService getConversionService() {
        return null;
    }

    @Override
    public <R extends Object> R read(Class<R> type, Entity source) {
        /*Object entity;
        try {
            Constructor<?> constructor = type.getConstructor();
            entity = constructor.newInstance();
        } catch (Exception exp) {
            exp.printStackTrace();
        }*/

        TypeInformation<R> typeInformation = ClassTypeInformation.from(type);
        TypeInformation<? extends R> typeToUse = dataStoreTypeMapper.readType(source, typeInformation);
        Class<? extends R> rawType = typeToUse.getType();
        if (Entity.class.isAssignableFrom(rawType)) {
            System.out.println(source);
        }
        return null;
    }

    @Override
    public void write(Object source, Entity sink) {

    }
}
