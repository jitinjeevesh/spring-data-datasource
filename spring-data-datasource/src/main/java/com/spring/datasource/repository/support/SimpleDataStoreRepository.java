package com.spring.datasource.repository.support;

import com.spring.datasource.core.DataStoreOperation;
import com.spring.datasource.repository.DataStoreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;

public class SimpleDataStoreRepository<T, ID extends Serializable> implements DataStoreRepository<T, ID> {

    private static final String ID_MUST_NOT_BE_NULL = "The given id must not be null!";
    private final DataStoreOperation dataStoreOperation;
    private final DataStoreEntityInformation<T, ID> dataStoreEntityInformation;

    public SimpleDataStoreRepository(DataStoreEntityInformation<T, ID> dataStoreEntityInformation, DataStoreOperation dataStoreOperation) {
        Assert.notNull(dataStoreOperation);
        Assert.notNull(dataStoreEntityInformation);
        this.dataStoreOperation = dataStoreOperation;
        this.dataStoreEntityInformation = dataStoreEntityInformation;
    }

    protected Class<T> getKindClass() {
        return dataStoreEntityInformation.getJavaType();
    }

    protected Class<ID> getIDClass() {
        return dataStoreEntityInformation.getIdType();
    }

    @Override
    public T findOne(ID id) {
        Assert.notNull(id, ID_MUST_NOT_BE_NULL);
        Class<T> domainType = getKindClass();
        if (id instanceof Long)
            return dataStoreOperation.load(domainType, (Long) id);
        else
            return dataStoreOperation.load(domainType, (String) id);
    }

    @Override
    public Iterable<T> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        Long count = count();
        List<T> list = dataStoreOperation.findAll(getKindClass(), pageable);
        return new PageImpl<T>(list, pageable, count);
    }

    @Override
    public <S extends T> S save(S entity) {
        Assert.notNull(entity, "Entity must not be null!");
        if (dataStoreEntityInformation.isNew(entity)) {
            dataStoreOperation.insert(entity, getKindClass());
        } else {
            dataStoreOperation.save(entity,getKindClass());
        }
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
    public List<T> findAll() {
        return dataStoreOperation.findAll(getKindClass());
    }

    @Override
    public Iterable<T> findAll(Iterable<ID> ids) {
        return null;
    }

    @Override
    public long count() {
        return dataStoreOperation.count(getKindClass());
    }

    @Override
    public void delete(ID id) {
        System.out.println("Deleted successfully by Id" + id);
    }

    @Override
    public void delete(T entity) {
        System.out.println("Entity Deleted successfully by Id");
    }

    @Override
    public void delete(Iterable<? extends T> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
