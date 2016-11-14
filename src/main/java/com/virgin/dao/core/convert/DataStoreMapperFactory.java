package com.virgin.dao.core.convert;

import com.google.cloud.datastore.*;
import com.virgin.dao.core.convert.mappers.EmbeddedValueDataStoreMapper;
import com.virgin.dao.core.convert.mappers.ListValueDataStoreMapper;
import com.virgin.dao.core.mapping.Embeddable;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.convert.support.GenericConversionService;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DataStoreMapperFactory {

    private static final DataStoreMapperFactory INSTANCE = new DataStoreMapperFactory();
    private GenericConversionService conversionService;
    private DataStoreCache<Type, DataStoreMapper> cache = null;
    private DataStoreCache<Type, Class<? extends Value<?>>> valueMapperCache = null;
    private Lock lock;

    public static DataStoreMapperFactory getInstance() {
        return INSTANCE;
    }

    private DataStoreMapperFactory() {
        conversionService = new DefaultConversionService();
        lock = new ReentrantLock();
        cache = new DataStoreCache<>();
        valueMapperCache = new DataStoreCache<>();
        createDefaultValueMappers();
        afterPropertiesSet();
    }

    private void createDefaultValueMappers() {
        valueMapperCache.put(boolean.class, BooleanValue.class);
        valueMapperCache.put(Boolean.class, BooleanValue.class);
        valueMapperCache.put(char.class, StringValue.class);
        valueMapperCache.put(Character.class, StringValue.class);
        valueMapperCache.put(short.class, LongValue.class);
        valueMapperCache.put(Short.class, LongValue.class);
        valueMapperCache.put(int.class, LongValue.class);
        valueMapperCache.put(Integer.class, LongValue.class);
        valueMapperCache.put(long.class, LongValue.class);
        valueMapperCache.put(Long.class, LongValue.class);
        valueMapperCache.put(float.class, DoubleValue.class);
        valueMapperCache.put(Float.class, DoubleValue.class);
        valueMapperCache.put(double.class, DoubleValue.class);
        valueMapperCache.put(Double.class, DoubleValue.class);
        valueMapperCache.put(String.class, StringValue.class);
        valueMapperCache.put(BigDecimal.class, DoubleValue.class);
        valueMapperCache.put(byte[].class, BlobValue.class);
        valueMapperCache.put(char[].class, StringValue.class);
        valueMapperCache.put(Date.class, DateTimeValue.class);
        valueMapperCache.put(Calendar.class, DateTimeValue.class);
    }

    public Class<? extends Value<?>> getMapperClass(Type type) {
        if (valueMapperCache == null) {
            createDefaultValueMappers();
        }
        return valueMapperCache.get(type);
    }

    public DataStoreMapper getMapper(Type type) {
        DataStoreMapper mapper = cache.get(type);
        if (mapper == null) {
            mapper = createCustomMappers(type);
        }
        return mapper;
    }

    private DataStoreMapper createCustomMappers(Type type) {
        lock.lock();
        try {
            DataStoreMapper mapper = cache.get(type);
            if (mapper != null) {
                return mapper;
            }
            if (type instanceof Class) {
                mapper = createMapper((Class<?>) type);
            } else if (type instanceof ParameterizedType) {
                mapper = createMapper((ParameterizedType) type);
            } else {
                throw new IllegalArgumentException(String.format("Type %s is neither a Class nor ParameterizedType", type));
            }
            cache.put(type, mapper);
            return mapper;
        } finally {
            lock.unlock();
        }
    }

    private DataStoreMapper createMapper(Class<?> clazz) {
        DataStoreMapper mapper;
        if (List.class.isAssignableFrom(clazz)) {
            mapper = new ListValueDataStoreMapper(conversionService, clazz);
        } else if (clazz.isAnnotationPresent(Embeddable.class)) {
            mapper = new EmbeddedValueDataStoreMapper(conversionService, clazz);
        } else {
            throw new RuntimeException("No Mapper found");
            //NO custom mapper found
        }
        return mapper;
    }

    private DataStoreMapper createMapper(ParameterizedType type) {
        Type rawType = type.getRawType();
        if (!(rawType instanceof Class)) {
            throw new IllegalArgumentException(String.format("Raw type of ParameterizedType is not a class: %s", type));
        }
        Class<?> rawClass = (Class<?>) rawType;
        DataStoreMapper mapper;
        if (List.class.isAssignableFrom(rawClass)) {
            mapper = new ListValueDataStoreMapper(conversionService, type);
        } else {
            throw new RuntimeException(String.format("Unsupported type: %s", type));
        }
        return mapper;
    }

    public void afterPropertiesSet() {
        DataStoreConverters.registerConverters(conversionService);
    }
}
