package com.virgin.dao.core.convert.mappers;

import com.google.cloud.datastore.ListValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;
import com.virgin.dao.core.convert.DataStoreMapper;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.data.util.TypeInformation;

import java.util.ArrayList;
import java.util.List;

public class ListValueDataStoreMapper implements DataStoreMapper {

    private GenericConversionService conversionService;
    private TypeInformation<?> typeInformation;

    public ListValueDataStoreMapper(GenericConversionService genericConversionService, TypeInformation<?> typeInformation) {
        this.conversionService = genericConversionService;
        this.typeInformation = typeInformation;
    }

    @Override
    public ValueBuilder<?, ?, ?> convert(Object input) {
        return null;
    }

    @Override
    public Object convert(Value<?> input) {
        List<? extends Value<?>> list = ((ListValue) input).get();
        List<Object> output = new ArrayList<>();
        /*if (Modifier.isAbstract(listClass.getModifiers())) {
            output = new ArrayList<>();
        } else {
            output = (List<Object>) IntrospectionUtils.instantiateObject(listClass);
        }*/

        System.out.println("....................Type Information?????????????????????.................................................");
        System.out.println(typeInformation.getActualType().getType());
        for (Value<?> item : list) {
            Object o = conversionService.convert(item, typeInformation.getActualType().getType());
            output.add(o);
        }
        return output;
    }
}
