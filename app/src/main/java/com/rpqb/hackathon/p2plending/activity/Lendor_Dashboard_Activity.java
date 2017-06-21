package com.rpqb.hackathon.p2plending.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.rpqb.hackathon.p2plending.R;
import com.rpqb.hackathon.p2plending.adapter.Dashboard_Adapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vikramv on 6/7/2017.
 */

public class Lendor_Dashboard_Activity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private Dashboard_Adapter dashboardAdapter;
    Toolbar toolbar;

    @BindView(R.id.dashboard_recyclerList)
    RecyclerView mRecylerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lendor_dashboard_activity);
        initToolBar();
        ButterKnife.bind(this);

        // for one column grid layout
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecylerView.setLayoutManager(mStaggeredGridLayoutManager);

        dashboardAdapter = new Dashboard_Adapter(this);
        mRecylerView.setAdapter(dashboardAdapter);
        dashboardAdapter.setmItemClickListener(onItemClickListener);
    }

    Dashboard_Adapter.OnItemClickListener onItemClickListener = new Dashboard_Adapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view) {
            Toast.makeText(Lendor_Dashboard_Activity.this, "Clicked ", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * Toolbar Initialization
     */
    public void initToolBar() {
        Log.d(TAG, "Dashboard InitToolBar");
        toolbar = (Toolbar) findViewById(R.id.dashboard_toolbar);
        toolbar.setTitle(R.string.dashboard_toolbarText);
        setSupportActionBar(toolbar);
    }
}