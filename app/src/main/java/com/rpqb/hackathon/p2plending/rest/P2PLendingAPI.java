package com.rpqb.hackathon.p2plending.rest;

import com.rpqb.hackathon.p2plending.model.BidInfo;
import com.rpqb.hackathon.p2plending.model.Project;
import com.rpqb.hackathon.p2plending.model.User;
import com.rpqb.hackathon.p2plending.transferobject.BidInfoTO;
import com.rpqb.hackathon.p2plending.transferobject.ProjectTO;
import com.rpqb.hackathon.p2plending.transferobject.ResponseTO;
import com.rpqb.hackathon.p2plending.transferobject.ResponseTOCampaign;

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
    Call<ResponseTOCampaign> getCampaignList();

    @POST("campaign/create")
    Call<ProjectTO> addNewProject(@Body ProjectTO projectTODetails);

    @POST("campaign/postbid")
    Call<ResponseTO> postBid(@Body BidInfoTO bidInfoTODetails);
}