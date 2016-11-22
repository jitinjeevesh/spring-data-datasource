package com.virgin.dao.repository.query;

import org.springframework.data.repository.query.ParametersParameterAccessor;

import java.util.Arrays;
import java.util.List;

/**
 * DataStore-specific {@link ParametersParameterAccessor} to allow access to the custom parameter.
 *
 * @author Jeevesh Pandey
 */
public class DataStoreParametersParameterAccessor extends ParametersParameterAccessor implements DataStoreParameterAccessor {

    private final DataStoreQueryMethod dataStoreQueryMethod;
    private final List<Object> values;

    /**
     * Creates a new {@link ParametersParameterAccessor}.
     *
     * @param dataStoreQueryMethod must not be {@literal null}.
     * @param values               must not be {@literal null}.
     */
    public DataStoreParametersParameterAccessor(DataStoreQueryMethod dataStoreQueryMethod, Object[] values) {
        super(dataStoreQueryMethod.getParameters(), values);
        this.dataStoreQueryMethod = dataStoreQueryMethod;
        this.values = Arrays.asList(values);
    }

    @Override
    public Object[] getValues() {
        return values.toArray();
    }
}
