package com.virgin.dao.repository.support;

import com.virgin.dao.repository.query.DataStoreEntityMetaData;
import org.springframework.data.repository.core.EntityInformation;

import java.io.Serializable;

public interface DataStoreEntityInformation<T, ID extends Serializable> extends EntityInformation<T, ID>,
        DataStoreEntityMetaData<T> {
}
