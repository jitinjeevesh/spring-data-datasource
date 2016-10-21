package com.virgin.dao;

import com.google.cloud.datastore.Entity;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.util.TypeInformation;

public class MappingDataStoreConverter extends AbstractDataStoreConverter {

    protected final MappingContext<? extends DataStorePersistentEntity<?>, DataStorePersistentProperty> mappingContext;

    public MappingDataStoreConverter(MappingContext<? extends DataStorePersistentEntity<?>, DataStorePersistentProperty> mappingContext) {
        this.mappingContext = mappingContext;
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
        return null;
    }

    @Override
    public void write(Object source, Entity sink) {

    }
}
