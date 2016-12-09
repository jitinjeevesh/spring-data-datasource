package com.virgin.repository;

import com.spring.datasource.repository.DataStoreRepository;
import com.virgin.domain.VcoValidationInfo;

import java.util.List;

public interface VcoValidationInfoRepository extends DataStoreRepository<VcoValidationInfo, Long> {

    VcoValidationInfo findByUserIdAndBrandName(Long userId, String brandName);
}
