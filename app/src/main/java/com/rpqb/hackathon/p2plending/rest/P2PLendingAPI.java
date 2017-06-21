package com.rpqb.hackathon.p2plending.rest;

import com.rpqb.hackathon.p2plending.model.User;
import com.rpqb.hackathon.p2plending.transferobject.JSONResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Vikramv on 6/21/2017.
 */

public interface P2PLendingAPI {
    @POST("registerUser")
    Call<JSONResponse> registerUser(@Body User userDetails);
}