package com.virgin.baseentity;

import org.springframework.data.annotation.Id;

public abstract class AbstractLongEntity extends AbstractCreatedUpdatedEntity {

    @Id
    private Long id;

    public AbstractLongEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}