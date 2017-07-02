package com.rpqb.hackathon.p2plending.model;

/**
 * Created by Vikramv on 7/2/2017.
 */

public class BidInfo {
    private int id;
    private int bidcreationtime;
    private int campaignid;
    private String userid;
    private double quote;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBidcreationtime() {
        return bidcreationtime;
    }

    public void setBidcreationtime(int bidcreationtime) {
        this.bidcreationtime = bidcreationtime;
    }

    public int getCampaignid() {
        return campaignid;
    }

    public void setCampaignid(int campaignid) {
        this.campaignid = campaignid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public double getQuote() {
        return quote;
    }

    public void setQuote(double quote) {
        this.quote = quote;
    }
}