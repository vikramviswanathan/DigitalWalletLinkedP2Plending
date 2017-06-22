package com.rpqb.hackathon.p2plending.rest;

import com.rpqb.hackathon.p2plending.model.User;
import com.rpqb.hackathon.p2plending.transferobject.ProjectTO;
import com.rpqb.hackathon.p2plending.transferobject.ResponseTO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Vikramv on 6/21/2017.
 */

public interface P2PLendingAPI {
    @POST("registerUser")
    Call<ResponseTO> registerUser(@Body User userDetails);

    @GET("campaign/campaignlist")
    Call<ArrayList<ProjectTO>> getCampaignList();
}