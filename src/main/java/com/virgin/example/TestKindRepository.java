package com.virgin.example;

import com.virgin.dao.repository.DataStoreRepository;
import com.virgin.dao.repository.Query;

import java.util.Date;
import java.util.List;

public interface TestKindRepository extends DataStoreRepository<TestKind, Long> {

    @Query(value = "select * from TestKind where name = @name and isActive = @isActive")
    TestKind findByName(String name, boolean isActive);

    List<TestKind> findAllByName(String name);

    TestKind findByNameAndBooleanPremitive(String name, Boolean aBoolean);

    TestKind findByNameAndCurrentDate(String name, Date date);
}
