package com.spring.datasource.core.convert;

import com.spring.datasource.core.exception.DataStoreEntityManagerException;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class DataStoreUtil {

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
            throw new IllegalArgumentException(String.format("Type %s is neither a Class nor a ParameterizeType", type));
        }
        return output;
    }

    public static Object instantiateObject(Class<?> clazz) {
        try {
            Constructor<?> constructor = clazz.getConstructor();
            return constructor.newInstance();
        } catch (Exception exp) {
            throw new DataStoreEntityManagerException(String.format("Unable to create %s instance", clazz));
        }
    }
}
