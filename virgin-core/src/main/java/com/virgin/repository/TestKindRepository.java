package com.virgin.repository;

import com.spring.datasource.repository.DataStoreRepository;
import com.spring.datasource.repository.Query;
import com.virgin.domain.TestKind;

import java.util.Date;
import java.util.List;

public interface TestKindRepository extends DataStoreRepository<TestKind, Long> {

    @Query(value = "select * from TestKind where name = @1 and isActive = @2")
    TestKind findByName(String name, boolean isActive);

    //    TestKind findByNameAndBooleanPremitive(String name, Boolean aBoolean);
    TestKind updateByName(Long id, String name);

    List<TestKind> findAllByName(String name);

    @Query(value = "select * from TestKind where currentDate < @CurrentDate")
    List<TestKind> findAllByCurrentDateLessThan(Date date);
    /* List<TestKind> findAllByName(String name);

    TestKind findByNameAndBooleanPremitive(String name, Boolean aBoolean);

    TestKind findByNameAndCurrentDate(String name, Date date);*/
}
