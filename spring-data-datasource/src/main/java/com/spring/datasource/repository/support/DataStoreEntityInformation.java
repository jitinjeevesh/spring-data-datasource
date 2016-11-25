package com.spring.datasource.repository.support;

import com.spring.datasource.repository.query.DataStoreEntityMetaData;
import org.springframework.data.repository.core.EntityInformation;

import java.io.Serializable;

public interface DataStoreEntityInformation<T, ID extends Serializable> extends EntityInformation<T, ID>,
        DataStoreEntityMetaData<T> {
}
