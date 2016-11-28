package com.virgin.domain;

import com.spring.datasource.core.mapping.Kind;
import com.virgin.baseentity.AbstractLongEntity;

@Kind
public class PointsInfo extends AbstractLongEntity {

    private Double totalPoints;
    //private Double				monthlyPoints;
    private Double janPoints;
    private Double febPoints;
    private Double marPoints;
    private Double aprPoints;
    private Double mayPoints;
    private Double junPoints;
    private Double julPoints;
    private Double augPoints;
    private Double sepPoints;
    private Double octPoints;
    private Double novPoints;
    private Double decPoints;
    private Integer year;


    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Double totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Double getJanPoints() {
        return janPoints;
    }

    public Double getFebPoints() {
        return febPoints;
    }

    public Double getMarPoints() {
        return marPoints;
    }

    public Double getAprPoints() {
        return aprPoints;
    }

    public Double getMayPoints() {
        return mayPoints;
    }

    public Double getJunPoints() {
        return junPoints;
    }

    public Double getJulPoints() {
        return julPoints;
    }

    public Double getAugPoints() {
        return augPoints;
    }

    public Double getSepPoints() {
        return sepPoints;
    }

    public Double getOctPoints() {
        return octPoints;
    }

    public Double getNovPoints() {
        return novPoints;
    }

    public Double getDecPoints() {
        return decPoints;
    }

    public void setJanPoints(Double janPoints) {
        this.janPoints = janPoints;
    }

    public void setFebPoints(Double febPoints) {
        this.febPoints = febPoints;
    }

    public void setMarPoints(Double marPoints) {
        this.marPoints = marPoints;
    }

    public void setAprPoints(Double aprPoints) {
        this.aprPoints = aprPoints;
    }

    public void setMayPoints(Double mayPoints) {
        this.mayPoints = mayPoints;
    }

    public void setJunPoints(Double junPoints) {
        this.junPoints = junPoints;
    }

    public void setJulPoints(Double julPoints) {
        this.julPoints = julPoints;
    }

    public void setAugPoints(Double augPoints) {
        this.augPoints = augPoints;
    }

    public void setSepPoints(Double sepPoints) {
        this.sepPoints = sepPoints;
    }

    public void setOctPoints(Double octPoints) {
        this.octPoints = octPoints;
    }

    public void setNovPoints(Double novPoints) {
        this.novPoints = novPoints;
    }

    public void setDecPoints(Double decPoints) {
        this.decPoints = decPoints;
    }

    @Override
    public String toString() {
        return "PointsInfo [totalPoints=" + totalPoints + ", janPoints="
                + janPoints + ", febPoints=" + febPoints + ", marPoints="
                + marPoints + ", aprPoints=" + aprPoints + ", mayPoints="
                + mayPoints + ", junPoints=" + junPoints + ", julPoints="
                + julPoints + ", augPoints=" + augPoints + ", sepPoints="
                + sepPoints + ", octPoints=" + octPoints + ", novPoints="
                + novPoints + ", decPoints=" + decPoints + ", year=" + year
                + "]";
    }

}
