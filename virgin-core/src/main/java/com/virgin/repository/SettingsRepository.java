package com.virgin.repository;

import com.spring.datasource.repository.DataStoreRepository;
import com.virgin.domain.Settings;

import java.util.List;

public interface SettingsRepository extends DataStoreRepository<Settings, Long> {

//    Settings findByFeature(String env);

    List<Settings> findAllByDefaultValueAndEnvironment(Boolean aBoolean, String s);
}
