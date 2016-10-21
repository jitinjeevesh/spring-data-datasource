package com.virgin.entity;

import com.jmethods.catatumbo.*;
import com.virgin.baseentity.AbstractLongEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@PropertyOverrides({
        @PropertyOverride(name = "contactInfo.city", property = @Property(
                name = "contact_city"))})
public class VirginRedUser extends AbstractLongEntity {

    @Property
    private String email;
    private String postcode;
    private Date dob;
    private String lastName;
    private String firstName;
    @Embedded
    private ContactInfo contactInfo;
    private String socialProviderId;
    private String gender;

    //    private List<String> roles;
//    private List<String> permissions;
    @Property
    private List<String> roles = new ArrayList<String>();
    ;
    @Property
    private String permissions;
    private Long state;
    private String imageUrl;
    //    private List<Long> partnerList = new ArrayList<>();
    @Property
    private List partnerList = new ArrayList<>();
    private boolean isImagePointCredit;
    private boolean isProfilePointCredit;
    private boolean isMembershipPointCredit;
    private String androidId;
    private String iosId;
    private String androidArn;
    private String iosArn;
    private String trackerName;
    private String locale;
    @Property
    private List<MasterBrand> masterBrands;
    private String city;
    private Double totalPoints;
    private Long attributedBrandId;
    private String attributedBrandName;
    private boolean enabled;
    private boolean backOfficeUser = false;

    private Long refBy;
    private String referCode;


    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getSocialProviderId() {
        return socialProviderId;
    }

    public void setSocialProviderId(String socialProviderId) {
        this.socialProviderId = socialProviderId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List getPartnerList() {
        return partnerList;
    }

    public void setPartnerList(List<Long> partnerList) {
        this.partnerList = partnerList;
    }

    public boolean isImagePointCredit() {
        return isImagePointCredit;
    }

    public void setImagePointCredit(boolean isImagePointCredit) {
        this.isImagePointCredit = isImagePointCredit;
    }

    public boolean isProfilePointCredit() {
        return isProfilePointCredit;
    }

    public void setProfilePointCredit(boolean isProfilePointCredit) {
        this.isProfilePointCredit = isProfilePointCredit;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public String getIosId() {
        return iosId;
    }

    public void setIosId(String iosId) {
        this.iosId = iosId;
    }

    public String getAndroidArn() {
        return androidArn;
    }

    public void setAndroidArn(String androidArn) {
        this.androidArn = androidArn;
    }

    public String getIosArn() {
        return iosArn;
    }

    public void setIosArn(String iosArn) {
        this.iosArn = iosArn;
    }

    public boolean isMembershipPointCredit() {
        return isMembershipPointCredit;
    }

    public void setMembershipPointCredit(boolean isMembershipPointCredit) {
        this.isMembershipPointCredit = isMembershipPointCredit;
    }

    public String getTrackerName() {
        return trackerName;
    }

    public void setTrackerName(String trackerName) {
        this.trackerName = trackerName;
    }

    public List<MasterBrand> getMasterBrands() {
        return masterBrands;
    }

    public void setMasterBrands(List<MasterBrand> masterBrands) {
        this.masterBrands = masterBrands;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Double totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Long getAttributedBrandId() {
        return attributedBrandId;
    }

    public void setAttributedBrandId(Long attributedBrandId) {
        this.attributedBrandId = attributedBrandId;
    }

    public String getAttributedBrandName() {
        return attributedBrandName;
    }

    public void setAttributedBrandName(String attributedBrandName) {
        this.attributedBrandName = attributedBrandName;
    }

    public List getRoles() {
        return roles;
    }

    public void setRoles(List roles) {
        this.roles = roles;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public boolean isBackOfficeUser() {
        return backOfficeUser;
    }

    public void setBackOfficeUser(boolean backOfficeUser) {
        this.backOfficeUser = backOfficeUser;
    }

    public Long getRefBy() {
        return refBy;
    }

    public void setRefBy(Long refBy) {
        this.refBy = refBy;
    }

    public String getReferCode() {
        return referCode;
    }

    public void setReferCode(String referCode) {
        this.referCode = referCode;
    }


    @Override
    public String toString() {
        return "VirginRedUser{" +
                "id=" + getId() +
                ", email='" + email + '\'' +
                ", roles='" + roles + '\'' +
                ", partnerlist='" + partnerList + '\'' +
                '}';
    }
}
