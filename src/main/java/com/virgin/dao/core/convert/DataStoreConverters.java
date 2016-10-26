package com.virgin.dao.core.convert;

import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class DataStoreConverters {

    private DataStoreConverters() {
    }

    public static Collection<Object> getConvertersToRegister() {

        List<Object> converters = new ArrayList<Object>();
        converters.add(IntegerToLongConverter.INSTANCE);
        return converters;
    }

    public static enum IntegerToLongConverter implements Converter<Integer, Long> {
        INSTANCE;

        public Long convert(Integer id) {
            return id == null ? null : (long) id;
        }
    }
}
