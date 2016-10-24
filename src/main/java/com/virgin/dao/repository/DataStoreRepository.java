package com.virgin.dao.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

@NoRepositoryBean
public interface DataStoreRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {
}
