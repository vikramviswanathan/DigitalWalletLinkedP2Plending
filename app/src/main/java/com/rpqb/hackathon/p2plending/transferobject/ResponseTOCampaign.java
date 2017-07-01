package com.rpqb.hackathon.p2plending.transferobject;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vikramv on 7/1/2017.
 */

public class ResponseTOCampaign {
    @SerializedName("status")
    private Integer status;
    @SerializedName("campaignlist")
    private ProjectTOBody campaignlist;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ProjectTOBody getCampaignlist() {
        return campaignlist;
    }

    public void setCampaignlist(ProjectTOBody campaignlist) {
        this.campaignlist = campaignlist;
    }
}