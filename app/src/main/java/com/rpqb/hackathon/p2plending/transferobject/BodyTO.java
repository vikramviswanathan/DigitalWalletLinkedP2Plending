package com.rpqb.hackathon.p2plending.transferobject;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Vikramv on 7/1/2017.
 */

public class BodyTO {
    @SerializedName("campaignlist")
    private ArrayList<ProjectTO> campaignlist;

    public ArrayList<ProjectTO> getCampaignlist() {
        return campaignlist;
    }

    public void setCampaignlist(ArrayList<ProjectTO> campaignlist) {
        this.campaignlist = campaignlist;
    }
}