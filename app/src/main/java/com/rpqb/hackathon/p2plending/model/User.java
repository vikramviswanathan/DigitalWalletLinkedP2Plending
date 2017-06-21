package com.rpqb.hackathon.p2plending.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vikramv on 6/21/2017.
 */

public class User {
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;
    @SerializedName("pan")
    private String pan;
    @SerializedName("aadhar")
    private String aadhar;
    @SerializedName("upi")
    private String upi;
    @SerializedName("usertype")
    private String usertype;
    @SerializedName("passpin")
    private String passpin;

    public User(String userName, String userEmail, String userPhone, String userPanNo, String userAadharNo,
                String userUpi, String userType, String userPassword) {
        this.name = userName;
        this.email = userEmail;
        this.phone = userPhone;
        this.pan = userPanNo;
        this.aadhar = userAadharNo;
        this.upi = userUpi;
        this.usertype = userType;
        this.passpin = userPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getUpi() {
        return upi;
    }

    public void setUpi(String upi) {
        this.upi = upi;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getPasspin() {
        return passpin;
    }

    public void setPasspin(String passpin) {
        this.passpin = passpin;
    }
}