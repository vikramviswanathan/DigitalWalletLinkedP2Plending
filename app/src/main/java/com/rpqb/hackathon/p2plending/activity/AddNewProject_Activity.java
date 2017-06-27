package com.rpqb.hackathon.p2plending.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rpqb.hackathon.p2plending.R;
import com.rpqb.hackathon.p2plending.model.Project;
import com.rpqb.hackathon.p2plending.rest.ApiClient;
import com.rpqb.hackathon.p2plending.rest.P2PLendingAPI;
import com.rpqb.hackathon.p2plending.transferobject.ResponseTO;
import com.rpqb.hackathon.p2plending.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vikramv on 6/26/2017.
 */

public class AddNewProject_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private final String TAG = this.getClass().getSimpleName();
    Toolbar toolbar;
    private ArrayAdapter<String> spinnerTermsOfRepaymentAdpater, spinnerNoOfInstallmentsAdapter;
    String termsOfRepayment, userID;
    SharedPreferences sharedPreferences;
    private P2PLendingAPI mP2PLendingAPIService;
    private boolean toShowTermsOfRepayment, toShowNoOfInstallments = false;

    @BindView(R.id.addnewproject_edTextTitle)
    EditText edTxtTitle;
    @BindView(R.id.addnewproject_edTextDescription)
    EditText edTxtDescription;
    @BindView(R.id.addnewproject_edTextAmount)
    EditText edTxtAmount;
    @BindView(R.id.addnewproject_edTextROI)
    EditText edTxtROI;
    @BindView(R.id.addnewproject_spinTermsOfRepayment)
    MaterialSpinner spinTermsOfRepayment;
    @BindView(R.id.addnewproject_spinNoOfInstallments)
    MaterialSpinner spinNoOfInstallments;
    @BindView(R.id.btn_addProject)
    Button btnAddProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnewproject_activity);
        initToolBar();
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences(Constants.AppSharedPreferences, Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("userID", "NULL");

        spinnerTermsOfRepaymentAdpater = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Constants.termsOfRepayment);
        spinnerTermsOfRepaymentAdpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinTermsOfRepayment.setAdapter(spinnerTermsOfRepaymentAdpater);
        spinnerTermsOfRepaymentAdpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinTermsOfRepayment.setOnItemSelectedListener(this);

        btnAddProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProject();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Borrower_Dashboard_Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(this, Borrower_Dashboard_Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        termsOfRepayment = spinTermsOfRepayment.getSelectedItem().toString();
        if (termsOfRepayment.equalsIgnoreCase(Constants.termsOfRepayment[0])) {
            // Monthly
            spinnerNoOfInstallmentsAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, Constants.monthlyInstallmentsList);
        } else {
            // Annually
            spinnerNoOfInstallmentsAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, Constants.annualInstallmentsList);
        }
        spinNoOfInstallments.setAdapter(spinnerNoOfInstallmentsAdapter);
        spinnerNoOfInstallmentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // TODO Auto-generated method stub
    }

    /**
     * Toolbar Initialization
     */
    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.addnewproject_toolbar);
        toolbar.setTitle(R.string.addnewproject_addNewProject);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_toolbar_arrow);
    }

    public void addProject() {
        Log.d(TAG, "AddNewProject");

        if (!validate()) {
            onAddProjectFailed();
            return;
        }

        btnAddProject.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(AddNewProject_Activity.this,
                R.style.AppTheme_Dark_Blue_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.addnewproject_loaderText));
        progressDialog.show();

        String title, description;
        float rateOfInterest;
        int amount, noOfInstallments;

        title = edTxtTitle.getText().toString();
        description = edTxtDescription.getText().toString();
        amount = Integer.parseInt(edTxtAmount.getText().toString());
        rateOfInterest = Float.parseFloat(edTxtROI.getText().toString());
        noOfInstallments = Integer.parseInt(spinNoOfInstallments.getSelectedItem().toString());

        Project projectDetails = new Project("", 0, userID, title, description, amount,
                rateOfInterest, noOfInstallments);

        Log.d(TAG, "Post Create Campaign: " + userID + " " + title + " " + description
                + " " + amount + " " + rateOfInterest + " " + noOfInstallments);

        mP2PLendingAPIService = ApiClient.getP2PLendingAPIService();
        Call mCall = mP2PLendingAPIService.addNewProject(projectDetails);
        mCall.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.d(TAG, "Create Campaign Response: " + response.body());
                ResponseTO jsonResponse = (ResponseTO) response.body();
                progressDialog.hide();
                onAddProjectSuccess(jsonResponse);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                progressDialog.hide();
                onAddProjectFailed();
                call.cancel();
            }
        });
    }

    /**
     * Validating all the fields on the Add new project page.
     *
     * @return
     */
    public boolean validate() {
        boolean valid = true;

        String userID = this.userID;
        String title = edTxtTitle.getText().toString();
        String description = edTxtDescription.getText().toString();
        String amount = edTxtAmount.getText().toString();
        String rateOfInterest = edTxtROI.getText().toString();
        String termsOfRepayment = spinTermsOfRepayment.getSelectedItem().toString();
        String noOfInstallments = spinNoOfInstallments.getSelectedItem().toString();

        // userID validation
        if (userID.isEmpty() || userID.equalsIgnoreCase("NULL")) {
            valid = false;
        }

        // Title validation
        if (title.isEmpty()) {
            edTxtTitle.setError(getResources().getString(R.string.addnewproject_titleError));
            valid = false;
        } else {
            edTxtTitle.setError(null);
        }

        // Description validation
        if (description.isEmpty()) {
            edTxtDescription.setError(getResources().getString(R.string.addnewproject_descriptionError));
            valid = false;
        } else {
            edTxtDescription.setError(null);
        }

        // Amount validation
        if (amount.isEmpty()) {
            edTxtAmount.setError(getResources().getString(R.string.addnewproject_amountError));
            valid = false;
        } else {
            edTxtAmount.setError(null);
        }

        // Rate of Interest validation
        if (rateOfInterest.isEmpty()) {
            edTxtROI.setError(getResources().getString(R.string.addnewproject_rateOfInterestError));
            valid = false;
        } else {
            edTxtROI.setError(null);
        }

        // Terms of Repayment validation
        if (termsOfRepayment.equals(getResources().getString(R.string.addnewproject_spinnerTermsOfRepaymentHint))) {
            activateError();
            valid = false;
        } else {
            toShowTermsOfRepayment = !toShowTermsOfRepayment;
            spinTermsOfRepayment.setError(null);
        }

        // No of Installments validation
        if (noOfInstallments.equals(getResources().getString(R.string.addnewproject_spinnerNoOfInstallmentsHint))) {
            activateError();
            valid = false;
        } else {
            toShowNoOfInstallments = !toShowNoOfInstallments;
            spinNoOfInstallments.setError(null);
        }

        return valid;
    }

    /**
     * Method to display toast message for Add New Project failure.
     */
    public void onAddProjectFailed() {
        Toast.makeText(getBaseContext(), "Create Campaign failed", Toast.LENGTH_LONG).show();
        btnAddProject.setEnabled(true);
    }

    /**
     * Method for handling Success add new project scenario.
     */
    public void onAddProjectSuccess(ResponseTO response) {
        btnAddProject.setEnabled(true);
        Log.d(TAG, "Success Response: " + response);

        if (response.getResponseStatus() == Constants.CREATED) {
            Intent intent = new Intent(AddNewProject_Activity.this,
                    Borrower_Dashboard_Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            AddNewProject_Activity.this.finish();
        }
    }

    public void activateError() {
        if (!toShowTermsOfRepayment) {
            spinTermsOfRepayment.setError(getResources().getString(R.string.addnewproject_termsOfRepaymentError));
        } else if (!toShowNoOfInstallments) {
            spinNoOfInstallments.setError(getResources().getString(R.string.addnewproject_noOfInstallmentsError));
        } else {
            spinTermsOfRepayment.setError(null);
            spinNoOfInstallments.setError(null);
        }
    }
}