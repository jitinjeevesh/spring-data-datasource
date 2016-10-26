package com.virgin.dao.core.convert.support;

import org.springframework.core.convert.converter.Converter;

public final class IntegerToLongConverter implements Converter<Integer, Long> {
    @Override
    public Long convert(Integer source) {
        return source.longValue();
    }
}