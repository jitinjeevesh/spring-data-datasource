package com.virgin.dao.core.convert.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.datastore.ListValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;
import com.virgin.dao.core.convert.DataStoreMapper;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.data.util.TypeInformation;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class EmbeddedValueDataStoreMapper implements DataStoreMapper {

    private GenericConversionService conversionService;
    private Class<?> clazz;

    public EmbeddedValueDataStoreMapper(GenericConversionService genericConversionService, Class<?> clazz) {
        this.conversionService = genericConversionService;
        this.clazz = clazz;
    }

    @Override
    public ValueBuilder<?, ?, ?> convert(Object input) {
        return null;
    }

    @Override
    public Object convert(Value<?> input) {
        Object output = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            output = mapper.readValue((String) input.get(), clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }
}
