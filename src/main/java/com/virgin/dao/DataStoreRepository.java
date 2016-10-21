package com.virgin.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

public interface DataStoreRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T,ID> {
}
