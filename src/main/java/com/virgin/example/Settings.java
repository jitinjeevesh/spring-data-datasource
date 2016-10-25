package com.virgin.example;

import com.virgin.dao.core.mapping.Kind;
import org.springframework.data.annotation.Id;

@Kind
public class Settings {

    @Id
    private Long id;
    private String environment;
    private String feature;
    private Integer value;
    private boolean defaultValue;

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public boolean isDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(boolean defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Settings{" +
                ", environment='" + environment + '\'' +
                ", feature='" + feature + '\'' +
                ", value=" + value +
                ", defaultValue=" + defaultValue +
                '}';
    }
}
