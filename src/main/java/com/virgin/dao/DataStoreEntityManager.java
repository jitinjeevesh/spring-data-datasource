package com.virgin.dao;

public interface DataStoreEntityManager {

    <E> E load(Class<E> entityClass, String id);

    <E> E load(Class<E> entityClass, long id);

    DataStoreConverter getConverter();
}
