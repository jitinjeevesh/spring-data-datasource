package com.virgin.example;

import com.virgin.dao.repository.DataStoreRepository;

import java.util.Date;
import java.util.List;

public interface TestKindRepository extends DataStoreRepository<TestKind, Long> {

    TestKind findByName(String name);

    List<TestKind> findAllByName(String name);

    TestKind findByNameAndBooleanPremitive(String name, Boolean aBoolean);

    TestKind findByNameAndCurrentDate(String name, Date date);
}
