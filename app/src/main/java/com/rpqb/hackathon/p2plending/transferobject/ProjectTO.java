package com.rpqb.hackathon.p2plending.transferobject;

/**
 * Created by Vikramv on 6/16/2017.
 */

public class ProjectTO {
    private String status;
    private int id;
    private String userid;
    private String title;
    private String description;
    private int loanamount;
    private float interest;
    private int noOfTerms;
    private int notermsremaining;

    public ProjectTO(String status, int id, String userid, String title, String description,
                     int loanamount, float interest, int noOfTerms, int notermsremaining) {
        this.status = status;
        this.id = id;
        this.userid = userid;
        this.title = title;
        this.description = description;
        this.loanamount = loanamount;
        this.interest = interest;
        this.noOfTerms = noOfTerms;
        this.notermsremaining = notermsremaining;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLoanamount() {
        return loanamount;
    }

    public void setLoanamount(int loanamount) {
        this.loanamount = loanamount;
    }

    public float getInterest() {
        return interest;
    }

    public void setInterest(float interest) {
        this.interest = interest;
    }

    public int getNoOfTerms() {
        return noOfTerms;
    }

    public void setNoOfTerms(int noOfTerms) {
        this.noOfTerms = noOfTerms;
    }

    public int getNotermsremaining() {
        return notermsremaining;
    }

    public void setNotermsremaining(int notermsremaining) {
        this.notermsremaining = notermsremaining;
    }
}