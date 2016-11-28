package com.virgin.domain;

import com.spring.datasource.core.mapping.Kind;
import com.virgin.baseentity.AbstractLongEntity;

@Kind
public class VcoValidationInfo extends AbstractLongEntity {

    private String email;
    private Long masterBrandId;
    private String validationResult;
    private long userId;
    private String brandName;
    private String cardNumber;
    private String segmentName;
    private String accountUId;
    private String mobileNo;
    private String firstName;
    private String lastName;
    private String postalCode;

    private String nameOnCard;
    private String panLastFour;
    private String dateOfBirth;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getMasterBrandId() {
        return masterBrandId;
    }

    public void setMasterBrandId(Long masterBrandId) {
        this.masterBrandId = masterBrandId;
    }

    public String getValidationResult() {
        return validationResult;
    }

    public void setValidationResult(String validationResult) {
        this.validationResult = validationResult;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getSegmentName() {
        return segmentName;
    }

    public void setSegmentName(String segmentName) {
        this.segmentName = segmentName;
    }

    public String getAccountUId() {
        return accountUId;
    }

    public void setAccountUId(String accountUId) {
        this.accountUId = accountUId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getPanLastFour() {
        return panLastFour;
    }

    public void setPanLastFour(String panLastFour) {
        this.panLastFour = panLastFour;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "VcoValidationInfo [email=" + email + ", validationResult=" + validationResult + ", userId=" + userId
                + ", brandName=" + brandName + ", cardNumber=" + cardNumber + ", segmentName=" + segmentName
                + ", accountUId=" + accountUId + ", mobileNo=" + mobileNo + ", postalCode=" + postalCode + "]";
    }
}
