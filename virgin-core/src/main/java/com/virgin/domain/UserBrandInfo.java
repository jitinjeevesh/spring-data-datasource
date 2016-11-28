package com.virgin.domain;

import com.spring.datasource.core.mapping.Kind;
import com.virgin.baseentity.AbstractLongEntity;

@Kind
public class UserBrandInfo extends AbstractLongEntity {

    private long userId;

    private long brandId;

    private String brandName;

    private String status;

    private boolean currentApplicableStatus;

    private String currentStatus;
    private String segmentName;

    private boolean triggerPoints;
    private String segmentValue;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getBrandId() {
        return brandId;
    }

    public void setBrandId(long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isCurrentApplicableStatus() {
        return currentApplicableStatus;
    }

    public void setCurrentApplicableStatus(boolean currentApplicableStatus) {
        this.currentApplicableStatus = currentApplicableStatus;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getSegmentName() {
        return segmentName;
    }

    public void setSegmentName(String segmentName) {
        this.segmentName = segmentName;
    }

    public boolean isTriggerPoints() {
        return triggerPoints;
    }

    public void setTriggerPoints(boolean triggerPoints) {
        this.triggerPoints = triggerPoints;
    }

    public String getSegmentValue() {
        return segmentValue;
    }

    public void setSegmentValue(String segmentValue) {
        this.segmentValue = segmentValue;
    }
}
