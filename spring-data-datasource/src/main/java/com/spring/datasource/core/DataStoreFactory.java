package com.spring.datasource.core;

import com.google.cloud.datastore.Datastore;

public interface DataStoreFactory {

    Datastore getDefaultDataStore();

}
