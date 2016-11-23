package com.virgin.example;

import com.virgin.dao.core.mapping.Embedded;
import com.virgin.dao.core.mapping.Kind;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Kind
public class  TestKind {
    @Id
    private Long id;
    private String name;
    @Embedded
    private ContactInfo contactInfo;
    private List<String> roles = new ArrayList<String>();
    private boolean booleanPremitive;
    private Boolean isActive;
    private Date currentDate;
    private KindType kindType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public boolean getBooleanPremitive() {
        return booleanPremitive;
    }

    public void setBooleanPremitive(boolean booleanPremitive) {
        this.booleanPremitive = booleanPremitive;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public KindType getKindType() {
        return kindType;
    }

    public void setKindType(KindType kindType) {
        this.kindType = kindType;
    }

    @Override
    public String toString() {
        return "TestKind{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contactInfo=" + contactInfo +
                ", roles=" + roles +
                ", booleanPremitive=" + booleanPremitive +
                ", isActive=" + isActive +
                ", currentDate=" + currentDate +
                ", kindType=" + kindType +
                '}';
    }
}
