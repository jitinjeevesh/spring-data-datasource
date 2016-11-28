package com.virgin.domain;

import com.spring.datasource.core.mapping.Kind;
import com.virgin.baseentity.AbstractLongEntity;

@Kind
public class Timeline extends AbstractLongEntity {

    private Long userId;
    private String description;
    private String action;
    private Integer points;
    private boolean isTimeline;
    private String contentType;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public boolean isTimeline() {
        return isTimeline;
    }

    public void setTimeline(boolean isTimeline) {
        this.isTimeline = isTimeline;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return "Timeline [userId=" + userId + ", description=" + description
                + ", action=" + action + ", points=" + points + ", isTimeline="
                + isTimeline + ", contentType=" + contentType + "]";
    }

}
