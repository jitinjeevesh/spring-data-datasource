package com.virgin.domain;

import com.spring.datasource.core.mapping.Kind;
import com.virgin.baseentity.AbstractLongEntity;

import java.util.Map;

@Kind
public class MasterBrand extends AbstractLongEntity {

    private String name;
    private String type;
    private String selectedImageUrl;
    private String unselectedImageUrl;
    private String transparentImageUrl;
    private Boolean allowFilter = false;
    private Boolean isActive = false;
    private String contact;
    private String email;
    private Boolean inMembership;
    private String partnerUrl;
    private boolean isSelected;
    private String partnerTncUrl;
    private String vouchers;

    private String teaserIconUrl;
    private String exclusiveTitle;

    private String validationInstructionText;
    private String teaserBackgroundUrl;
    private Map<Integer, String> segments;

    private String redemptionCodeUrl;
    private Boolean isValidatable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSelectedImageUrl() {
        return selectedImageUrl;
    }

    public void setSelectedImageUrl(String selectedImageUrl) {
        this.selectedImageUrl = selectedImageUrl;
    }

    public String getUnselectedImageUrl() {
        return unselectedImageUrl;
    }

    public void setUnselectedImageUrl(String unselectedImageUrl) {
        this.unselectedImageUrl = unselectedImageUrl;
    }

    public Boolean getAllowFilter() {
        return allowFilter;
    }

    public void setAllowFilter(Boolean allowFilter) {
        this.allowFilter = allowFilter;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getInMembership() {
        return inMembership;
    }

    public void setInMembership(Boolean inMembership) {
        this.inMembership = inMembership;
    }

    public String getPartnerUrl() {
        return partnerUrl;
    }

    public void setPartnerUrl(String partnerUrl) {
        this.partnerUrl = partnerUrl;
    }

    public String getPartnerTncUrl() {
        return partnerTncUrl;
    }

    public void setPartnerTncUrl(String partnerTncUrl) {
        this.partnerTncUrl = partnerTncUrl;
    }

    public String getTransparentImageUrl() {
        return transparentImageUrl;
    }

    public void setTransparentImageUrl(String transparentImageUrl) {
        this.transparentImageUrl = transparentImageUrl;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getTeaserIconUrl() {
        return teaserIconUrl;
    }

    public void setTeaserIconUrl(String teaserIconUrl) {
        this.teaserIconUrl = teaserIconUrl;
    }

    public String getTeaserBackgroundUrl() {
        return teaserBackgroundUrl;
    }

    public void setTeaserBackgroundUrl(String teaserBackgroundUrl) {
        this.teaserBackgroundUrl = teaserBackgroundUrl;
    }

    public String getVouchers() {
        return vouchers;
    }

    public void setVouchers(String vouchers) {
        this.vouchers = vouchers;
    }

    public String getExclusiveTitle() {
        return exclusiveTitle;
    }

    public void setExclusiveTitle(String exclusiveTitle) {
        this.exclusiveTitle = exclusiveTitle;
    }

    public String getValidationInstructionText() {
        return validationInstructionText;
    }

    public void setValidationInstructionText(String validationInstructionText) {
        this.validationInstructionText = validationInstructionText;
    }

    public Map<Integer, String> getSegments() {
        return segments;
    }

    public void setSegments(Map<Integer, String> segments) {
        this.segments = segments;
    }

    public String getRedemptionCodeUrl() {
        return redemptionCodeUrl;
    }

    public void setRedemptionCodeUrl(String redemptionCodeUrl) {
        this.redemptionCodeUrl = redemptionCodeUrl;
    }

    public Boolean getIsValidatable() {
        return isValidatable;
    }

    public void setIsValidatable(Boolean isValidatable) {
        this.isValidatable = isValidatable;
    }

    @Override
    public String toString() {
        return "MasterBrand{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", selectedImageUrl='" + selectedImageUrl + '\'' +
                ", unselectedImageUrl='" + unselectedImageUrl + '\'' +
                ", transparentImageUrl='" + transparentImageUrl + '\'' +
                ", allowFilter=" + allowFilter +
                ", isActive=" + isActive +
                ", contact='" + contact + '\'' +
                ", email='" + email + '\'' +
                ", inMembership=" + inMembership +
                ", partnerUrl='" + partnerUrl + '\'' +
                ", isSelected=" + isSelected +
                ", partnerTncUrl='" + partnerTncUrl + '\'' +
                ", vouchers='" + vouchers + '\'' +
                ", teaserIconUrl='" + teaserIconUrl + '\'' +
                ", exclusiveTitle='" + exclusiveTitle + '\'' +
                ", validationInstructionText='" + validationInstructionText + '\'' +
                ", teaserBackgroundUrl='" + teaserBackgroundUrl + '\'' +
                ", segments=" + segments +
                ", redemptionCodeUrl='" + redemptionCodeUrl + '\'' +
                ", isValidatable=" + isValidatable +
                '}';
    }
}
