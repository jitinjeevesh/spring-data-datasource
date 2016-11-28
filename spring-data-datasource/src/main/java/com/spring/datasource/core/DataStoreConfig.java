package com.spring.datasource.core;

import org.springframework.beans.factory.annotation.Value;

public class DataStoreConfig {

    @Value("${spring.data.datasource.connection:default}")
    private String connection;

    @Value("${spring.data.datasource.namespace:}")
    private String namespace;

    @Value("${spring.data.datasource.projectId:}")
    private String projectId;

    @Value("${spring.data.datasource.jsonFile:}")
    private String jsonFile;

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getJsonFile() {
        return jsonFile;
    }

    public void setJsonFile(String jsonFile) {
        this.jsonFile = jsonFile;
    }
}
