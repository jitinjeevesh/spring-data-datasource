package com.virgin.dao.core;

import com.virgin.dao.core.convert.DataStoreConverter;
import com.virgin.dao.core.query.DataStoreQuery;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DataStoreOperation {

    <E> void insert(Object objectToSave, Class<E> entityClass);

    <E> E load(Class<E> entityClass, String id);

    <E> E load(Class<E> entityClass, long id);

    <E> List<E> findAll(Class<E> entityClass);

    <E> List<E> findAll(Class<E> entityClass, Pageable pageable);

    DataStoreConverter getConverter();

    long count(Class<?> entityClass);

    long count(DataStoreQuery dataStoreQuery, Class<?> type, String kindName);

    <E> E findOne(DataStoreQuery dataStoreQuery, Class<E> type, String kindName);
}
