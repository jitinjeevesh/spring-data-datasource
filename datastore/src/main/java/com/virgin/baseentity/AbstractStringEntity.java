package com.virgin.baseentity;

public abstract class AbstractStringEntity extends AbstractCreatedUpdatedEntity {
    private String id;

    public AbstractStringEntity() {
    }

    public AbstractStringEntity(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String subString() {
        return String.format("id:%s", new Object[]{this.id});
    }
}
