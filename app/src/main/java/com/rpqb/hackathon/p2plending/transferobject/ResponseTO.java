package com.rpqb.hackathon.p2plending.transferobject;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vikramv on 6/21/2017.
 */

public class ResponseTO {
    @SerializedName("status")
    private Integer responseStatus;
    @SerializedName("message")
    private String responseMessage;

    public ResponseTO(Integer responseStatus, String responseMessage) {
        this.responseStatus = responseStatus;
        this.responseMessage = responseMessage;
    }

    public Integer getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(Integer responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}