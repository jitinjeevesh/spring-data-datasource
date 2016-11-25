package com.spring.datasource.core;

import com.spring.datasource.core.convert.DataStoreConverter;
import com.spring.datasource.core.query.DataStoreQuery;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DataStoreOperation {

    <E> void insert(Object objectToSave, Class<E> entityClass);

    <E> void save(Object objectToSave, Class<E> entityClass);

    <E> E load(Class<E> entityClass, String id);

    <E> E load(Class<E> entityClass, long id);

    <E> List<E> findAll(Class<E> entityClass);

    <E> List<E> findAll(Class<E> entityClass, Pageable pageable);

    DataStoreConverter getConverter();

    long count(Class<?> entityClass);

    long count(DataStoreQuery dataStoreQuery, Class<?> type, String kindName);

    <E> E findOne(DataStoreQuery dataStoreQuery, Class<E> type, String kindName);

    <E> E update(DataStoreQuery dataStoreQuery, Class<E> type, String kindName);

    <E> List<E> findAll(DataStoreQuery query, Pageable pageable, Class<E> type, String kindName);
}
