package com.virgin.dao.core.convert;

import com.google.cloud.datastore.*;
import com.virgin.dao.core.convert.mappers.ListValueDataStoreMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DataStoreMapperFactory {

    private static final DataStoreMapperFactory INSTANCE = new DataStoreMapperFactory();
    private GenericConversionService conversionService;
    private DataStoreCache<TypeInformation<?>, DataStoreMapper> cache = null;
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

    public DataStoreMapper getMapper(TypeInformation<?> type) {
        DataStoreMapper mapper = cache.get(type);
        if (mapper == null) {
            mapper = createCustomMappers(type);
        }
        System.out.println(mapper);
        return mapper;
    }

    private DataStoreMapper createCustomMappers(TypeInformation<?> type) {
        lock.lock();
        System.out.println(type.getActualType());
        System.out.println(type.getType());
        try {
            DataStoreMapper mapper = cache.get(type);
            if (mapper != null) {
                return mapper;
            }
            if (type.getType() instanceof Class) {
                mapper = createMapper(type);
            } /*else if (type instanceof ParameterizedType) {
                System.out.println("Inside instance of ParameterizedType");
                mapper = createMapper((ClassTypeInformation<?>) type);
            } */ else {
                throw new IllegalArgumentException(String.format("Type %s is neither a Class nor ParameterizedType", type));
            }
            cache.put(type, mapper);
            return mapper;
        } finally {
            lock.unlock();
        }
    }

    private DataStoreMapper createMapper(TypeInformation<?> typeInformation) {
        DataStoreMapper mapper;
        if (List.class.isAssignableFrom(typeInformation.getType())) {
            mapper = new ListValueDataStoreMapper(conversionService, typeInformation);
        } else {
            throw new RuntimeException("No Mapper found");
            //NO custom mapper found
        }
        return mapper;
    }

    public void afterPropertiesSet() {
        DataStoreConverters.registerConverters(conversionService);
    }
}
