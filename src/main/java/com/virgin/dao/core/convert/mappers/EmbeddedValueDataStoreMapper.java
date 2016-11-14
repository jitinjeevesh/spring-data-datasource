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
        System.out.println(">>>>>>>>>>>>>>>>>Inside embedded converter>>>>>>>>>>>>>>>>>>>");
        Object output = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            output = mapper.readValue((String) input.get(), clazz);
            System.out.println("........................Output...........................");
            System.out.println(clazz);
            System.out.println(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*for (Value<?> item : list) {
            Object o = conversionService.convert(item, typeInformation.getActualType().getType());
            output.add(o);
        }*/
        return output;
    }
}
