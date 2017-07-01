package com.rpqb.hackathon.p2plending.transferobject;

import com.google.gson.annotations.SerializedName;

import retrofit2.http.Body;

/**
 * Created by Vikramv on 7/1/2017.
 */

public class ProjectTOBody {
    @SerializedName("statusCode")
    private Integer statusCode;
    @SerializedName("body")
    private BodyTO body;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public BodyTO getBody() {
        return body;
    }

    public void setBody(BodyTO body) {
        this.body = body;
    }
}