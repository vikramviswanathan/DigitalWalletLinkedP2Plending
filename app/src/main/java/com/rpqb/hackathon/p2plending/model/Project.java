package com.rpqb.hackathon.p2plending.model;

/**
 * Created by Vikramv on 6/21/2017.
 */

public class Project {
    private String userid;
    private String title;
    private String description;
    private int loanamount;
    private float interest;
    private int noOfTerms;

    public Project(String userid, String title, String description, int loanamount,
                   float interest, int noOfTerms) {
        this.userid = userid;
        this.title = title;
        this.description = description;
        this.loanamount = loanamount;
        this.interest = interest;
        this.noOfTerms = noOfTerms;
    }

    public Project() {

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
}