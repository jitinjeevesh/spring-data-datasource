package com.spring.datasource.core;

import com.google.cloud.AuthCredentials;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.spring.datasource.core.convert.DataStoreUtil;
import com.spring.datasource.core.exception.DataStoreEntityManagerFactoryException;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * A factory class for producing {@link Datastore}s.
 *
 * @author Jeevesh Pandey
 */
public class SimpleDataStoredFactory implements DataStoreFactory {

    private DataStoreConfig dataStoreConfig;

    public SimpleDataStoredFactory(DataStoreConfig dataStoreConfig) {
        this.dataStoreConfig = dataStoreConfig;
    }

    public Datastore getDefaultDataStore() {
        if (dataStoreConfig.getConnection().equalsIgnoreCase("file")) {
            ClassLoader classLoader = getClass().getClassLoader();
            URL url = classLoader.getResource(dataStoreConfig.getJsonFile());
            Assert.notNull(url, "Data store credential file must not be nullls");
            File file = new File(url.getFile());
            return createEntityManager(dataStoreConfig.getProjectId(), file, dataStoreConfig.getNamespace());
        } else {
            return createDefaultEntityManager(null);
        }
    }

    public Datastore createDefaultEntityManager(String namespace) {
        try {
            AuthCredentials authCredentials = AuthCredentials.createApplicationDefaults();
            DatastoreOptions.Builder datastoreOptionsBuilder = DatastoreOptions.newBuilder()
                    .setAuthCredentials(authCredentials);
            if (namespace != null) {
                datastoreOptionsBuilder.namespace(namespace);
            }
            return datastoreOptionsBuilder.build().getService();
        } catch (Exception exp) {
            throw new DataStoreEntityManagerFactoryException(exp);
        }
    }

    public Datastore createEntityManager(String projectId, File jsonCredentialsFile) {
        return createEntityManager(projectId, jsonCredentialsFile, null);
    }

    public Datastore createEntityManager(String projectId, File jsonCredentialsFile, String namespace) {
        try {
            return createEntityManager(projectId, new FileInputStream(jsonCredentialsFile), namespace);
        } catch (Exception exp) {
            throw new DataStoreEntityManagerFactoryException(exp);
        }
    }

    public Datastore createEntityManager(String projectId, InputStream jsonCredentialsStream, String namespace) {
        try {
            AuthCredentials authCredentials = AuthCredentials.createForJson(jsonCredentialsStream);
            DatastoreOptions.Builder datastoreOptionsBuilder = DatastoreOptions.newBuilder().setAuthCredentials(authCredentials);
            if (projectId != null && !projectId.equals("")) {
                datastoreOptionsBuilder.setProjectId(projectId);
            }
            if (namespace != null && !namespace.equals("")) {
                datastoreOptionsBuilder.namespace(namespace);
            }
            return datastoreOptionsBuilder.build().getService();
        } catch (Exception exp) {
            throw new DataStoreEntityManagerFactoryException(exp);
        } finally {
            DataStoreUtil.close(jsonCredentialsStream);
        }
    }
}
