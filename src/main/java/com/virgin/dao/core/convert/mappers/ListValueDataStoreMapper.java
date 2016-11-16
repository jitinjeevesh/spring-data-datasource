package com.virgin.dao.core.convert.mappers;

import com.google.cloud.datastore.ListValue;
import com.google.cloud.datastore.NullValue;
import com.google.cloud.datastore.Value;
import com.virgin.dao.core.convert.DataStoreMapper;
import com.virgin.dao.core.convert.DataStoreMapperFactory;
import com.virgin.dao.core.convert.DataStoreUtil;
import org.springframework.core.convert.support.GenericConversionService;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListValueDataStoreMapper implements DataStoreMapper {

    private Type type;
    private Class<?> listClass;
    private Class<?> itemClass;
    private DataStoreMapper dataStoreMapper;

    public ListValueDataStoreMapper(Type type) {
        this.type = type;
        Class<?>[] classArray = DataStoreUtil.resolveCollectionType(type);
        this.listClass = classArray[0];
        this.itemClass = classArray[1];
        this.dataStoreMapper = DataStoreMapperFactory.getInstance().getMapper(itemClass);
    }

    @Override
    public Value<?> convert(Object input) {
        List<?> list = (List<?>) input;
        ListValue.Builder listValurBuilder = ListValue.newBuilder();
        for (Object item : list) {
            listValurBuilder.addValue(dataStoreMapper.convert(item));
        }
        return listValurBuilder.build();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object convert(Value<?> input) {
        List<? extends Value<?>> list = ((ListValue) input).get();
        List<Object> output;
        if (Modifier.isAbstract(listClass.getModifiers())) {
            output = new ArrayList<>();
        } else {
            output = (List<Object>) DataStoreUtil.instantiateObject(listClass);
        }
        for (Value<?> item : list) {
            Object o = dataStoreMapper.convert(item);
            output.add(o);
        }
        return output;
    }
}
