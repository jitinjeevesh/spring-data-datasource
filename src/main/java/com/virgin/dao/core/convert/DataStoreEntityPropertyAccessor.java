package com.virgin.dao.core.convert;

import com.google.cloud.datastore.Entity;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.TypedValue;

import java.util.Map;

class DataStoreEntityPropertyAccessor extends MapAccessor {

    static final MapAccessor INSTANCE = new DataStoreEntityPropertyAccessor();

    @Override
    public Class<?>[] getSpecificTargetClasses() {
        return new Class[]{Entity.class};
    }

    @Override
    public boolean canRead(EvaluationContext context, Object target, String name) {
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public TypedValue read(EvaluationContext context, Object target, String name) {

        Map<String, Object> source = (Map<String, Object>) target;

        Object value = source.get(name);
        return value == null ? TypedValue.NULL : new TypedValue(value);
    }
}
