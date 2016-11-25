package com.spring.datasource.core.convert.mappers;

import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.Value;
import com.spring.datasource.core.convert.DataStoreMapper;
import org.springframework.core.convert.support.GenericConversionService;

public class EnumValueDataStoreMapper implements DataStoreMapper {

    private GenericConversionService conversionService;
    private Class aClass;

    public EnumValueDataStoreMapper(GenericConversionService genericConversionService, Class aClass) {
        this.aClass = aClass;
        this.conversionService = genericConversionService;
    }

    @Override
    public Value<?> convert(Object input) {
        return conversionService.convert(input.toString(), StringValue.class);
    }

    @Override
    public Object convert(Value<?> input) {
        String value = ((StringValue) input).get();
        return Enum.valueOf(aClass, value);
    }
}
