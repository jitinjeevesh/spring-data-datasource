package com.virgin.dao.core.convert.mappers;

import com.google.cloud.datastore.ListValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;
import com.virgin.dao.core.convert.DataStoreMapper;
import org.springframework.core.convert.support.GenericConversionService;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListValueDataStoreMapper implements DataStoreMapper {

    private GenericConversionService conversionService;
    private Type type;
    private Class<?> listClass;
    private Class<?> itemClass;

    public ListValueDataStoreMapper(GenericConversionService genericConversionService, Type type) {
        Class<?>[] classArray = resolveCollectionType(type);
        this.conversionService = genericConversionService;
        this.listClass = classArray[0];
        this.itemClass = classArray[1];
    }

    @Override
    public ValueBuilder<?, ?, ?> convert(Object input) {
        return null;
    }

    @Override
    public Object convert(Value<?> input) {
        List<? extends Value<?>> list = ((ListValue) input).get();
        List<Object> output = new ArrayList<>();
        if (Modifier.isAbstract(listClass.getModifiers())) {
            output = new ArrayList<>();
        } else {
            output = (List<Object>) instantiateObject(listClass);
        }
        for (Value<?> item : list) {
            Object o = conversionService.convert(item, itemClass);
            output.add(o);
        }
        return output;
    }

    public static Class<?>[] resolveCollectionType(Type type) {
        Class<?>[] output = new Class[2];
        if (type instanceof Class) {
            output[0] = (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] argTypes = parameterizedType.getActualTypeArguments();
            output[0] = (Class<?>) parameterizedType.getRawType();
            if (argTypes != null && argTypes.length == 1 && argTypes[0] instanceof Class) {
                output[1] = (Class<?>) argTypes[0];
            }
        } else {
            throw new IllegalArgumentException(String.format("Type %s is neither a Class nor a ParameterizedType", type));
        }
        return output;
    }

    public static Object instantiateObject(Class<?> clazz) {
        try {
            Constructor<?> constructor = clazz.getConstructor();
            return constructor.newInstance();
        } catch (Exception exp) {
            throw new RuntimeException(exp);
        }
    }

}
