package com.virgin.repository;

import com.spring.datasource.repository.DataStoreRepository;
import com.virgin.domain.Settings;

public interface SettingsRepository extends DataStoreRepository<Settings, Long> {

//    Settings findByFeature(String env);
}
