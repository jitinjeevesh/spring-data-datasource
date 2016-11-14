package com.virgin.dao.core.convert.mappers;

import com.google.cloud.datastore.ListValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;
import com.virgin.dao.core.convert.DataStoreMapper;
import com.virgin.dao.core.convert.DataStoreUtil;
import org.springframework.core.convert.support.GenericConversionService;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListValueDataStoreMapper implements DataStoreMapper {

    private GenericConversionService conversionService;
    private Type type;
    private Class<?> listClass;
    private Class<?> itemClass;

    public ListValueDataStoreMapper(GenericConversionService genericConversionService, Type type) {
        this.type = type;
        this.conversionService = genericConversionService;
        Class<?>[] classArray = DataStoreUtil.resolveCollectionType(type);
        this.listClass = classArray[0];
        this.itemClass = classArray[1];
    }

    @Override
    public ValueBuilder<?, ?, ?> convert(Object input) {
        return null;
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
            if (item instanceof ListValue) {
                Object o = convert(item);
                output.add(o);
            } else {
                Object o = conversionService.convert(item, itemClass);
                output.add(o);
            }
        }
        return output;
    }
}
