package com.spring.datasource.core;

import com.google.cloud.AuthCredentials;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.spring.datasource.core.exception.DataStoreEntityManagerFactoryException;

/**
 * A factory class for producing {@link Datastore}s.
 *
 * @author Jeevesh Pandey
 */
//TODO: Implement more factory method to create instance via JSON credentials.
public class SimpleDataStoredFactory implements DataStoreFactory {

    private static final SimpleDataStoredFactory INSTANCE = new SimpleDataStoredFactory();

    public static SimpleDataStoredFactory getInstance() {
        return INSTANCE;
    }

    public Datastore getDefaultDataStore() {
        return createDefaultEntityManager(null);
    }

    public Datastore createDefaultEntityManager(String namespace) {
        try {
            AuthCredentials authCredentials = AuthCredentials.createApplicationDefaults();
            DatastoreOptions.Builder datastoreOptionsBuilder = DatastoreOptions.builder()
                    .authCredentials(authCredentials);
            if (namespace != null) {
                datastoreOptionsBuilder.namespace(namespace);
            }
            //TODO:Exception thrown.
            return datastoreOptionsBuilder.build().service();
        } catch (Exception exp) {
            throw new DataStoreEntityManagerFactoryException(exp);
        }
    }

}
