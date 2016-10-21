package com.virgin.baseentity;

import com.jmethods.catatumbo.Identifier;
import com.jmethods.catatumbo.MappedSuperClass;

@MappedSuperClass
public abstract class AbstractLongEntity extends AbstractCreatedUpdatedEntity {

    @Identifier
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