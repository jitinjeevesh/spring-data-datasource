package com.virgin.entity;

import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.Identifier;
import com.jmethods.catatumbo.Property;
import com.virgin.baseentity.AbstractLongEntity;

@Entity
public class Settings extends AbstractLongEntity {

    @Property
    private String environment;
    @Property
    private String feature;
    @Property
    private Integer value;
    @Property
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

    @Override
    public String toString() {
        return "Settings{" +
                "id='" + getId() + '\'' +
                ", environment='" + environment + '\'' +
                ", feature='" + feature + '\'' +
                ", value=" + value +
                ", defaultValue=" + defaultValue +
                '}';
    }
}
