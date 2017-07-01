package com.rpqb.hackathon.p2plending.utils;

/**
 * Created by Vikramv on 6/14/2017.
 */

public class Constants {
    public Constants() {
        super();
    }

    public static final String AppSharedPreferences = "P2PLending";
    public static final String[] userTypeList = {"Lender", "Borrower"};
    public static final String[] kycDocumentsList = {"PAN Card", "Aadhar"};
    public static final String[] termsOfRepayment = {"Monthly", "Annually"};
    public static final String[] monthlyInstallmentsList = {"12", "24", "36", "48", "60"};
    public static final String[] annualInstallmentsList = {"1", "2", "3", "4", "5"};

    // Campaign status
    public static final String ACTIVE = "Active";

    // Development API BASE_URL (Bluemix)
    public static final String BASE_URL = "http://119.81.218.178:8080/";
    // public static final String BASE_URL = "http://192.168.0.24:3001/";

    // JSON Response status
    public static final int CREATED = 201;
}