package com.virgin.dao;

import org.springframework.data.repository.core.EntityInformation;

import java.io.Serializable;

public interface DataStoreEntityInformation<T, ID extends Serializable> extends EntityInformation<T, ID> {

    String getKindName();
}
