package com.virgin.dao.core.convert.mappers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.datastore.NullValue;
import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.Value;
import com.virgin.dao.core.convert.DataStoreMapper;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.data.annotation.Transient;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class EmbeddedValueDataStoreMapper implements DataStoreMapper {

    private GenericConversionService conversionService;
    private Class<?> clazz;

    public EmbeddedValueDataStoreMapper(GenericConversionService genericConversionService, Class<?> clazz) {
        this.conversionService = genericConversionService;
        this.clazz = clazz;
    }

    @Override
    public Value<?> convert(Object input) {
        ObjectMapper mapper = new ObjectMapper();
        Set<Field> fields = findFields(clazz, Transient.class);
        String jsonInString = null;
        try {
            jsonInString = mapper.writeValueAsString(input);
            HashMap hashMap = mapper.readValue(jsonInString, HashMap.class);
            if (!fields.isEmpty()) {
                for (Field field : fields) {
                    hashMap.remove(field.getName());
                }
            }
            jsonInString = mapper.writeValueAsString(hashMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (jsonInString == null) {
            return NullValue.newBuilder().build();
        }
        return conversionService.convert(jsonInString, StringValue.class);
    }

    @Override
    public Object convert(Value<?> input) {
        Object output = null;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            output = mapper.readValue((String) input.get(), clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    public static Set<Field> findFields(Class<?> classs, Class<? extends Annotation> ann) {
        Set<Field> set = new HashSet<>();
        Class<?> c = classs;
        while (c != null) {
            for (Field field : c.getDeclaredFields()) {
                if (field.isAnnotationPresent(ann)) {
                    set.add(field);
                }
            }
            c = c.getSuperclass();
        }
        return set;
    }
}
