package com.virgin.dao.core;

import com.google.cloud.datastore.Datastore;

public interface DataStoreFactory {

    Datastore getDefaultDataStore();
}
