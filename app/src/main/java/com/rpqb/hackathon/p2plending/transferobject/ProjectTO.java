package com.rpqb.hackathon.p2plending.transferobject;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Vikramv on 6/16/2017.
 */

public class ProjectTO {
    @SerializedName("status")
    private String status;
    @SerializedName("id")
    private Integer id;
    @SerializedName("userid")
    private String userid;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("loanamount")
    private Integer loanamount;
    @SerializedName("interest")
    private Double interest;
    @SerializedName("noOfTerms")
    private Integer noOfTerms;
    @SerializedName("bidlist")
    private ArrayList<BidInfoTO> bidlist;
    @SerializedName("bidinfo")
    private BidInfoTO bidinfo;
    @SerializedName("notermsremaining")
    private Integer notermsremaining;

    public ProjectTO(String status, Integer id, String userid, String title, String description,
                     Integer loanamount, Double interest, Integer noOfTerms, ArrayList<BidInfoTO> bidlist,
                     BidInfoTO bidinfo, Integer notermsremaining) {
        this.status = status;
        this.id = id;
        this.userid = userid;
        this.title = title;
        this.description = description;
        this.loanamount = loanamount;
        this.interest = interest;
        this.noOfTerms = noOfTerms;
        this.bidlist = bidlist;
        this.bidinfo = bidinfo;
        this.notermsremaining = notermsremaining;
    }

    public ProjectTO() {}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getLoanamount() {
        return loanamount;
    }

    public void setLoanamount(Integer loanamount) {
        this.loanamount = loanamount;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Integer getNoOfTerms() {
        return noOfTerms;
    }

    public void setNoOfTerms(Integer noOfTerms) {
        this.noOfTerms = noOfTerms;
    }

    public ArrayList<BidInfoTO> getBidlist() {
        return bidlist;
    }

    public void setBidlist(ArrayList<BidInfoTO> bidlist) {
        this.bidlist = bidlist;
    }

    public BidInfoTO getBidinfo() {
        return bidinfo;
    }

    public void setBidinfo(BidInfoTO bidinfo) {
        this.bidinfo = bidinfo;
    }

    public Integer getNotermsremaining() {
        return notermsremaining;
    }

    public void setNotermsremaining(Integer notermsremaining) {
        this.notermsremaining = notermsremaining;
    }
}