package com.virgin.dao.repository.support;

import com.virgin.dao.core.DataStoreOperation;
import com.virgin.dao.core.exception.IDPropertyNotFoundException;
import com.virgin.dao.repository.DataStoreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import java.io.Serializable;

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

    //TODO:Change this method for string also.
    @Override
    public T findOne(ID id) {
        Assert.notNull(id, ID_MUST_NOT_BE_NULL);
        System.out.println("..................................................");
        System.out.println(getIDClass());
        Class<T> domainType = getKindClass();
        System.out.println(getKindClass());
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
        return null;
    }

    @Override
    public <S extends T> S save(S entity) {
        System.out.println("Entity fetched successfully");
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
