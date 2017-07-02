package com.rpqb.hackathon.p2plending.transferobject;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vikramv on 7/1/2017.
 */

public class BidInfoTO {
    @SerializedName("id")
    private Integer id;
    @SerializedName("bidcreationtime")
    private Integer bidcreationtime;
    @SerializedName("campaignid")
    private Integer campaignid;
    @SerializedName("userid")
    private String userid;
    @SerializedName("quote")
    private Double quote;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBidcreationtime() {
        return bidcreationtime;
    }

    public void setBidcreationtime(Integer bidcreationtime) {
        this.bidcreationtime = bidcreationtime;
    }

    public Integer getCampaignid() {
        return campaignid;
    }

    public void setCampaignid(Integer campaignid) {
        this.campaignid = campaignid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Double getQuote() {
        return quote;
    }

    public void setQuote(Double quote) {
        this.quote = quote;
    }
}