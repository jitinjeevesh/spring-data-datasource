package com.virgin.example;

import com.virgin.dao.repository.DataStoreRepository;

public interface SettingsRepository extends DataStoreRepository<Settings, Long> {

    Settings findByFeature(String env);
}
