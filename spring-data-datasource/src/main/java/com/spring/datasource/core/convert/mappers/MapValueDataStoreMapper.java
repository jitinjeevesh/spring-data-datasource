package com.spring.datasource.core.convert.mappers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.datastore.*;
import com.spring.datasource.core.convert.DataStoreMapper;
import com.spring.datasource.core.convert.DataStoreMapperFactory;
import com.spring.datasource.core.convert.DataStoreUtil;
import com.spring.datasource.core.exception.DataStoreMappingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.*;

public class MapValueDataStoreMapper implements DataStoreMapper {

    private Type type;
    private Class<?> mapClass;
    private Class<?> keyClass;
    private Class<?> valueClass;
    private DataStoreMapper dataStoreMapper;

    public MapValueDataStoreMapper(Type type) {
        this.type = type;
        Class<?>[] classArray = DataStoreUtil.resolveMapType(type);
        mapClass = classArray[0];
        keyClass = classArray[1] == null ? String.class : classArray[1];
        if (!(keyClass.equals(String.class) || keyClass.equals(Integer.class))) {
            throw new DataStoreMappingException(String.format("Unsupported type %s for Map's key. Keys must be of type %s", keyClass.getName(), String.class.getName()));
        }
        valueClass = classArray[2];
        this.dataStoreMapper = DataStoreMapperFactory.getInstance().getMapper(valueClass);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Value<?> convert(Object input) {
        Map<String, ?> map = (Map<String, ?>) input;
        FullEntity.Builder<IncompleteKey> entityBuilder = FullEntity.newBuilder();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            String key = entry.getKey();
            entityBuilder.set(key, dataStoreMapper.convert(entry.getValue()));
        }
        return EntityValue.newBuilder(entityBuilder.build()).build();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object convert(Value<?> input) {
        Map<Object, Object> map;
        if (Modifier.isAbstract(mapClass.getModifiers())) {
            if (SortedMap.class.equals(mapClass)) {
                map = new TreeMap<>();
            } else {
                map = new HashMap<>();
            }
        } else {
            map = (Map<Object, Object>) DataStoreUtil.instantiateObject(mapClass);
        }
        if (StringValue.class.isAssignableFrom(input.getClass())) {
            Object o = input.get();
            map = toMap(new JSONObject((String) o));
        } else {
            EntityValue entityValue = (EntityValue) input;
            FullEntity<?> entity = entityValue.get();
            for (String property : entity.getNames()) {
                map.put(property, dataStoreMapper.convert(entity.getValue(property)));
            }
        }
        return map;
    }

    private Map<Object, Object> toMap(JSONObject object) throws JSONException {
        Map<Object, Object> map = new HashMap<Object, Object>();

        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);
            if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

}
