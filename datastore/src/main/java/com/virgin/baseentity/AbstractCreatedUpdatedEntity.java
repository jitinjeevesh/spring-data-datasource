package com.virgin.baseentity;

import com.jmethods.catatumbo.MappedSuperClass;
import com.jmethods.catatumbo.Property;

import java.io.Serializable;
import java.util.Date;

@MappedSuperClass
public abstract class AbstractCreatedUpdatedEntity implements Serializable {
    @Property
    private String createdBy;
    @Property
    private Date createdDate;
    @Property
    private String updatedBy;
    @Property
    private Date updatedDate;

    public AbstractCreatedUpdatedEntity() {
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

}
