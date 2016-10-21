package com.virgin.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import java.io.Serializable;

public class SimpleDataStoreRepository<T, ID extends Serializable> implements DataStoreRepository<T, ID> {

    private static final String ID_MUST_NOT_BE_NULL = "The given id must not be null!";
    private final DataStoreEntityManager dataStoreEntityManager;
    private final DataStoreEntityInformation<T, ID> dataStoreEntityInformation;

    public SimpleDataStoreRepository(DataStoreEntityInformation<T, ID> dataStoreEntityInformation, DataStoreEntityManager dataStoreEntityManager) {
        Assert.notNull(dataStoreEntityManager);
        Assert.notNull(dataStoreEntityInformation);
        this.dataStoreEntityManager = dataStoreEntityManager;
        this.dataStoreEntityInformation = dataStoreEntityInformation;
    }

    protected Class<T> getKindClass() {
        return dataStoreEntityInformation.getJavaType();
    }

    protected Class<ID> getIDClass() {
        return dataStoreEntityInformation.getIdType();
    }

    //TODO:Change this method for string also.
    @Override
    public T findOne(ID id) {
        Assert.notNull(id, ID_MUST_NOT_BE_NULL);
        Class<T> domainType = getKindClass();
        return dataStoreEntityManager.load(domainType, (Long) id);
    }

    @Override
    public Iterable<T> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends T> S save(S entity) {
        return null;
    }

    @Override
    public <S extends T> Iterable<S> save(Iterable<S> entities) {
        return null;
    }

    @Override
    public boolean exists(ID id) {
        return false;
    }

    @Override
    public Iterable<T> findAll() {
        return null;
    }

    @Override
    public Iterable<T> findAll(Iterable<ID> ids) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(ID id) {

    }

    @Override
    public void delete(T entity) {

    }

    @Override
    public void delete(Iterable<? extends T> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
