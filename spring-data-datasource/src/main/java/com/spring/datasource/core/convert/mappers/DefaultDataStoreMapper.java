package com.spring.datasource.core.convert.mappers;

import com.google.cloud.datastore.NullValue;
import com.google.cloud.datastore.Value;
import com.spring.datasource.core.convert.DataStoreMapper;
import org.springframework.core.convert.support.GenericConversionService;

public class DefaultDataStoreMapper implements DataStoreMapper {

    private GenericConversionService conversionService;
    private Class<? extends Value<?>> dataStoreClass;
    private Class<?> javaClass;

    public DefaultDataStoreMapper(GenericConversionService genericConversionService, Class<?> javaClass, Class<? extends Value<?>> dataStoreClass) {
        this.dataStoreClass = dataStoreClass;
        this.javaClass = javaClass;
        this.conversionService = genericConversionService;
    }

    @Override
    public Value<?> convert(Object input) {
        if (input == null) {
            return NullValue.newBuilder().build();
        }
        return conversionService.convert(input, dataStoreClass);
    }

    @Override
    public Object convert(Value<?> input) {
        return conversionService.convert(input, javaClass);
    }
}
