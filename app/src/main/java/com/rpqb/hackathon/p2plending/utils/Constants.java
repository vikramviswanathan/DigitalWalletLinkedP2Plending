package com.rpqb.hackathon.p2plending.utils;

/**
 * Created by Vikramv on 6/14/2017.
 */

public class Constants {
    public Constants() {
        super();
    }

    public static final String[] userTypeList = {"Lender", "Borrower"};
    public static final String[] kycDocumentsList = {"PAN Card", "Aadhar"};

    // Development API BASE_URL
    public static final String BASE_URL = "http://192.168.0.24:3001/";

    //JSON Response status
    public static final int CREATED = 201;
}