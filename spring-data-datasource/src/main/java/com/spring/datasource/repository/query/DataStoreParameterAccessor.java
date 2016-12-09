package com.spring.datasource.repository.query;

import org.springframework.data.repository.query.ParameterAccessor;

public interface DataStoreParameterAccessor extends ParameterAccessor {

    Object[] getValues();
}