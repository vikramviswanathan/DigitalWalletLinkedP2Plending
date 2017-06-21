package com.rpqb.hackathon.p2plending.rest;

import com.rpqb.hackathon.p2plending.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Vikramv on 6/21/2017.
 */

public class ApiClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static P2PLendingAPI getP2PLendingAPIService() {
        return ApiClient.getClient().create(P2PLendingAPI.class);
    }
}