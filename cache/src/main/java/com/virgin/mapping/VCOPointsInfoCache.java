package com.virgin.mapping;

import java.io.Serializable;

public class VCOPointsInfoCache implements Serializable {

    private static final long serialVersionUID = -1l;

    private Long id;
    private Long brandId;
    private String brandName;
    private String status;
    private String timePeriod;
    private Integer validationPoints;
    private Boolean isValidatable = false;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
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

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    public Integer getValidationPoints() {
        return validationPoints;
    }

    public void setValidationPoints(Integer validationPoints) {
        this.validationPoints = validationPoints;
    }

    public Boolean getIsValidatable() {
        return isValidatable;
    }

    public void setIsValidatable(Boolean isValidatable) {
        this.isValidatable = isValidatable;
    }
}