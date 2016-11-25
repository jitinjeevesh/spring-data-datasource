package com.spring.datasource.core.convert.mappers;

import com.google.cloud.datastore.ListValue;
import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;
import com.spring.datasource.core.convert.DataStoreMapperFactory;
import com.spring.datasource.core.convert.DataStoreMapper;
import com.spring.datasource.core.convert.DataStoreUtil;
import org.springframework.core.convert.support.GenericConversionService;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.*;

public class SetValueDataStoreMapper implements DataStoreMapper {

    private Type type;
    private Class<?> setClass;
    private Class<?> itemClass;
    private DataStoreMapper dataStoreMapper;

    public SetValueDataStoreMapper(Type type) {
        this.type = type;
        Class<?>[] classArray = DataStoreUtil.resolveCollectionType(type);
        this.setClass = classArray[0];
        this.itemClass = classArray[1];
        this.dataStoreMapper = DataStoreMapperFactory.getInstance().getMapper(itemClass);
    }

    @Override
    public Value<?> convert(Object input) {
        Set<?> set = (Set<?>) input;
        ListValue.Builder listValurBuilder = ListValue.newBuilder();
        for (Object item : set) {
            listValurBuilder.addValue(dataStoreMapper.convert(item));
        }
        return listValurBuilder.build();
    }

    //TODO:Not yet tested.
    @SuppressWarnings("unchecked")
    @Override
    public Object convert(Value<?> input) {
        if (StringValue.class.isAssignableFrom(input.getClass())) {
            return dataStoreMapper.convert(input);
        }
        List<? extends Value<?>> list = ((ListValue) input).get();
        Set<Object> output;
        if (Modifier.isAbstract(setClass.getModifiers())) {
            if (SortedSet.class.isAssignableFrom(setClass)) {
                output = new TreeSet<>();
            } else {
                output = new HashSet<>();
            }
        } else {
            output = (Set<Object>) DataStoreUtil.instantiateObject(setClass);
        }
        for (Value<?> item : list) {
            Object o = dataStoreMapper.convert(item);
            output.add(o);
        }
        return output;
    }
}
