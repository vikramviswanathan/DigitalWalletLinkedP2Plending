package com.rpqb.hackathon.p2plending.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.rpqb.hackathon.p2plending.R;
import com.rpqb.hackathon.p2plending.adapter.Dashboard_Adapter;
import com.rpqb.hackathon.p2plending.model.Project;
import com.rpqb.hackathon.p2plending.rest.ApiClient;
import com.rpqb.hackathon.p2plending.rest.P2PLendingAPI;
import com.rpqb.hackathon.p2plending.transferobject.ProjectTO;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vikramv on 6/23/2017.
 */

public class Borrower_Dashboard_Activity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private Dashboard_Adapter dashboardAdapter;
    Toolbar toolbar;
    private ArrayList<Project> projectList = new ArrayList<Project>();
    private P2PLendingAPI mP2PLendingAPIService;

    @BindView(R.id.borrower_dashboard_recyclerList)
    RecyclerView mRecylerView;
    @BindView(R.id.borrower_dashboard_floatingActionButton)
    FloatingActionButton fabAddProject;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrower_dashboard_activity);
        init();
        ButterKnife.bind(this);

        // for one column grid layout
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecylerView.setLayoutManager(mStaggeredGridLayoutManager);

        dashboardAdapter = new Dashboard_Adapter(this, projectList);
        mRecylerView.setAdapter(dashboardAdapter);
        dashboardAdapter.setmItemClickListener(onItemClickListener);
        fabAddProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Add Project Clicked");
                //Toast.makeText(Borrower_Dashboard_Activity.this, "Clicked ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Borrower_Dashboard_Activity.this,
                        AddNewProject_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                Borrower_Dashboard_Activity.this.finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    Dashboard_Adapter.OnItemClickListener onItemClickListener = new Dashboard_Adapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view) {
            Toast.makeText(Borrower_Dashboard_Activity.this, "Clicked ", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * Toolbar and Project List Initialization
     */
    public void init() {
        Log.d(TAG, "Dashboard Init");
        toolbar = (Toolbar) findViewById(R.id.borrower_dashboard_toolbar);
        toolbar.setTitle(R.string.dashboard_toolbarText);
        setSupportActionBar(toolbar);

        mP2PLendingAPIService = ApiClient.getP2PLendingAPIService();
        Call mCall = mP2PLendingAPIService.getCampaignList();
        mCall.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                ArrayList<ProjectTO> projectTOList = (ArrayList<ProjectTO>) response.body();
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
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }
}