package com.spring.datasource.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface DataStoreRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {

    List<T> findAll();

    List<T> findAll(Integer limit);
}
