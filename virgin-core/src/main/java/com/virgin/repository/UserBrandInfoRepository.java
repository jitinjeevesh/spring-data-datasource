package com.virgin.repository;

import com.spring.datasource.repository.DataStoreRepository;
import com.virgin.domain.UserBrandInfo;

import java.util.List;

public interface UserBrandInfoRepository extends DataStoreRepository<UserBrandInfo, Long> {

    List<UserBrandInfo> findAllByCurrentApplicableStatus(Boolean status);

    List<UserBrandInfo> findAllByCurrentApplicableStatusAndCurrentStatus(Boolean status, String currentStatus);
}
