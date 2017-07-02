package com.rpqb.hackathon.p2plending.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rpqb.hackathon.p2plending.R;
import com.rpqb.hackathon.p2plending.adapter.Lendor_Dashboard_Adapter;
import com.rpqb.hackathon.p2plending.model.BidInfo;
import com.rpqb.hackathon.p2plending.model.Project;
import com.rpqb.hackathon.p2plending.rest.ApiClient;
import com.rpqb.hackathon.p2plending.rest.P2PLendingAPI;
import com.rpqb.hackathon.p2plending.transferobject.BidInfoTO;
import com.rpqb.hackathon.p2plending.transferobject.BodyTO;
import com.rpqb.hackathon.p2plending.transferobject.ProjectTO;
import com.rpqb.hackathon.p2plending.transferobject.ProjectTOBody;
import com.rpqb.hackathon.p2plending.transferobject.ResponseTOCampaign;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vikramv on 6/7/2017.
 */

public class Lendor_Dashboard_Activity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private Lendor_Dashboard_Adapter dashboardAdapter;
    Toolbar toolbar;
    private ArrayList<Project> projectList = new ArrayList<Project>();
    private P2PLendingAPI mP2PLendingAPIService;

    @BindView(R.id.lendor_dashboard_recyclerList)
    RecyclerView mRecylerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lendor_dashboard_activity);
        init();
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                Intent intent = new Intent(Lendor_Dashboard_Activity.this,
                        Login_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                Lendor_Dashboard_Activity.this.finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    Lendor_Dashboard_Adapter.OnItemClickListener onItemClickListener = new Lendor_Dashboard_Adapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view) {
            final int itemPostion = mRecylerView.getChildLayoutPosition(view);
            // Toast.makeText(Lendor_Dashboard_Activity.this, "Clicked " + itemPostion, Toast.LENGTH_SHORT).show();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    Lendor_Dashboard_Activity.this, R.style.AppTheme_Dark_Blue_Dialog);

            TextView txtVwTitle = new TextView(Lendor_Dashboard_Activity.this);
            txtVwTitle.setText(getResources().getString(R.string.dashboard_dialogPostBid));
            txtVwTitle.setGravity(Gravity.CENTER_HORIZONTAL);
            txtVwTitle.setTextSize(20);
            alertDialogBuilder.setCustomTitle(txtVwTitle);

            // Set up the input
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(20, 20, 20, 20);
            EditText edTxtInput = new EditText(Lendor_Dashboard_Activity.this);
            // Specify the type of input expected; this, for example, sets the input as a password,
            // and will mask the text
            edTxtInput.setInputType(InputType.TYPE_CLASS_TEXT);
            edTxtInput.setLayoutParams(layoutParams);
            alertDialogBuilder.setView(edTxtInput);

            // Set up the buttons
            alertDialogBuilder.setPositiveButton(getResources().getString(
                    R.string.dashboard_dialogOk), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    
                    Toast.makeText(Lendor_Dashboard_Activity.this, "Bid Submitted",
                            Toast.LENGTH_SHORT).show();
                }
            });
            alertDialogBuilder.setNegativeButton(getResources().getString(
                    R.string.dashboard_dialogCancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            alertDialogBuilder.show();
        }
    };

    /**
     * Toolbar and Project List Initialization
     */
    public void init() {
        Log.d(TAG, "Dashboard Init");
        toolbar = (Toolbar) findViewById(R.id.lendor_dashboard_toolbar);
        toolbar.setTitle("Lendor " + getResources().getString(R.string.dashboard_toolbarText));
        setSupportActionBar(toolbar);

        mP2PLendingAPIService = ApiClient.getP2PLendingAPIService();
        final ProgressDialog progressDialog = new ProgressDialog(Lendor_Dashboard_Activity.this,
                R.style.AppTheme_Dark_Blue_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.dashboard_loaderText));
        progressDialog.show();

        Call<ResponseTOCampaign> mCall = mP2PLendingAPIService.getCampaignList();
        mCall.enqueue(new Callback<ResponseTOCampaign>() {
            @Override
            public void onResponse(Call<ResponseTOCampaign> call,
                                   Response<ResponseTOCampaign> response) {
                ResponseTOCampaign responseTOCampaign = response.body();
                ProjectTOBody projectTOBody = responseTOCampaign.getCampaignlist();
                BodyTO bodyTO = projectTOBody.getBody();
                ArrayList<ProjectTO> projectTOList = bodyTO.getCampaignlist();
                ArrayList<BidInfo> bidInfoList = new ArrayList<BidInfo>();

                if (projectTOList != null) {
                    for (ProjectTO projectTO : projectTOList) {
                        Project project = new Project();
                        project.setId(projectTO.getId());
                        project.setUserid(projectTO.getUserid());
                        project.setDescription(projectTO.getDescription());
                        project.setInterest(projectTO.getInterest());
                        project.setTitle(projectTO.getTitle());
                        project.setNoOfTerms(projectTO.getNoOfTerms());
                        project.setLoanamount(projectTO.getLoanamount());

                        BidInfo bidInfo = new BidInfo();
                        bidInfo.setId(projectTO.getBidinfo().getId());
                        bidInfo.setBidcreationtime(projectTO.getBidinfo().getBidcreationtime());
                        bidInfo.setCampaignid(projectTO.getBidinfo().getCampaignid());
                        bidInfo.setQuote(projectTO.getBidinfo().getQuote());
                        bidInfo.setUserid(projectTO.getBidinfo().getUserid());

                        if (projectTO.getBidlist() != null) {
                            for (BidInfoTO bidListTOInfo : projectTO.getBidlist()) {
                                BidInfo bidListInfo = new BidInfo();
                                bidListInfo.setId(bidListTOInfo.getId());
                                bidListInfo.setBidcreationtime(bidListTOInfo.getBidcreationtime());
                                bidListInfo.setCampaignid(bidListTOInfo.getCampaignid());
                                bidListInfo.setQuote(bidListTOInfo.getQuote());
                                bidListInfo.setUserid(bidListTOInfo.getUserid());

                                bidInfoList.add(bidListInfo);
                            }
                        }
                        project.setBidInfo(bidInfo);
                        project.setBidlist(bidInfoList);

                        projectList.add(project);
                    }
                }
                Log.d(TAG, "CampaignList Size: " + projectList.size());
                // for one column grid layout
                mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(1,
                        StaggeredGridLayoutManager.VERTICAL);
                mRecylerView.setLayoutManager(mStaggeredGridLayoutManager);

                dashboardAdapter = new Lendor_Dashboard_Adapter(Lendor_Dashboard_Activity.this,
                        projectList);
                mRecylerView.setAdapter(dashboardAdapter);
                dashboardAdapter.setmItemClickListener(onItemClickListener);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseTOCampaign> call, Throwable t) {
            }
        });
    }
}