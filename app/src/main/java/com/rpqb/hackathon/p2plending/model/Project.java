package com.rpqb.hackathon.p2plending.model;

/**
 * Created by Vikramv on 6/21/2017.
 */

public class Project {
    private String status;
    private int id;
    private String userid;
    private String title;
    private String description;
    private int loanamount;
    private double interest;
    private int noOfTerms;

    public Project(String status, int id, String userid, String title, String description, int loanamount,
                   double interest, int noOfTerms) {
        this.status = status;
        this.id = id;
        this.userid = userid;
        this.title = title;
        this.description = description;
        this.loanamount = loanamount;
        this.interest = interest;
        this.noOfTerms = noOfTerms;
    }

    public Project() {
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

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public int getNoOfTerms() {
        return noOfTerms;
    }

    public void setNoOfTerms(int noOfTerms) {
        this.noOfTerms = noOfTerms;
    }
}