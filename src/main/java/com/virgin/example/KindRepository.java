package com.virgin.example;

import com.virgin.dao.repository.DataStoreRepository;

public interface KindRepository extends DataStoreRepository<Settings, Long> {

    Settings findByEnviromentAndFeature(String env,String feature);
}
