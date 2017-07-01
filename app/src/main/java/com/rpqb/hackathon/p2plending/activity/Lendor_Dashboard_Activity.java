package com.rpqb.hackathon.p2plending.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.rpqb.hackathon.p2plending.R;
import com.rpqb.hackathon.p2plending.adapter.Lendor_Dashboard_Adapter;
import com.rpqb.hackathon.p2plending.model.Project;
import com.rpqb.hackathon.p2plending.rest.ApiClient;
import com.rpqb.hackathon.p2plending.rest.P2PLendingAPI;
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
    private String m_Text = "";

    @BindView(R.id.lendor_dashboard_recyclerList)
    RecyclerView mRecylerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lendor_dashboard_activity);
        init();
        ButterKnife.bind(this);
    }

    Lendor_Dashboard_Adapter.OnItemClickListener onItemClickListener = new Lendor_Dashboard_Adapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view) {
            // Toast.makeText(Lendor_Dashboard_Activity.this, "Clicked ", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(Lendor_Dashboard_Activity.this,
                    R.style.AppTheme_Dark_Blue_Dialog);
            builder.setTitle("Post Bid");

            // Set up the input
            final EditText input = new EditText(Lendor_Dashboard_Activity.this);
            // Specify the type of input expected; this, for example, sets the input as a password,
            // and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    m_Text = input.getText().toString();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
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
            public void onResponse(Call<ResponseTOCampaign> call, Response<ResponseTOCampaign> response) {
                ResponseTOCampaign responseTOCampaign = response.body();
                ProjectTOBody projectTOBody = responseTOCampaign.getCampaignlist();
                BodyTO bodyTO = projectTOBody.getBody();

                ArrayList<ProjectTO> projectTOList = bodyTO.getCampaignlist();

                for (ProjectTO projectTO : projectTOList) {
                    Project project = new Project();
                    project.setUserid(projectTO.getUserid());
                    project.setDescription(projectTO.getDescription());
                    project.setInterest(projectTO.getInterest());
                    project.setTitle(projectTO.getTitle());
                    project.setNoOfTerms(projectTO.getNoOfTerms());
                    project.setLoanamount(projectTO.getLoanamount());

                    projectList.add(project);
                }
                Log.d(TAG, "CampaignList Size: " + projectList.size());
                // for one column grid layout
                mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                mRecylerView.setLayoutManager(mStaggeredGridLayoutManager);

                dashboardAdapter = new Lendor_Dashboard_Adapter(Lendor_Dashboard_Activity.this, projectList);
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