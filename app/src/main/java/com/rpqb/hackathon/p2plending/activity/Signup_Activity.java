package com.rpqb.hackathon.p2plending.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rpqb.hackathon.p2plending.R;
import com.rpqb.hackathon.p2plending.model.User;
import com.rpqb.hackathon.p2plending.rest.ApiClient;
import com.rpqb.hackathon.p2plending.rest.P2PLendingAPI;
import com.rpqb.hackathon.p2plending.transferobject.JSONResponse;
import com.rpqb.hackathon.p2plending.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vikramv on 6/8/2017.
 */

public class Signup_Activity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private boolean toShowKYCDoc, toShowUserType = false;
    Toolbar toolbar;
    private ArrayAdapter<String> spinnerKYCAdpater, spinnerUserAdapter;
    private P2PLendingAPI mP2PLendingAPIService;

    @BindView(R.id.signup_spinUserType)
    MaterialSpinner spinUserType;
    @BindView(R.id.signup_edTextFirstName)
    EditText edTxtFirstName;
    @BindView(R.id.signup_edTextLastName)
    EditText edtTxtLastName;
    @BindView(R.id.signup_edTextPhoneNo)
    EditText edTxtPhoneNo;
    @BindView(R.id.signup_spinKYCDoc)
    MaterialSpinner spinKYCDoc;
    @BindView(R.id.signup_edTextKYCNo)
    EditText edTxtKYCNo;
    @BindView(R.id.signup_edTextEmail)
    EditText edTxtEmail;
    @BindView(R.id.signup_edTextUpi)
    EditText edTxtUpi;
    @BindView(R.id.signup_edTextPassword)
    EditText edTxtPassword;
    @BindView(R.id.signup_edTextRePassword)
    EditText edTxtRePassword;
    @BindView(R.id.btn_signup)
    Button btnSignUp;
    @BindView(R.id.signup_txtVwLogin)
    TextView txtVwLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        initToolBar();
        ButterKnife.bind(this);

        spinnerUserAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                Constants.userTypeList);
        spinnerKYCAdpater = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                Constants.kycDocumentsList);
        spinnerUserAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKYCAdpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinUserType.setAdapter(spinnerUserAdapter);
        spinKYCDoc.setAdapter(spinnerKYCAdpater);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

        txtVwLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Login activity
                Log.d(TAG, "Login Text clicked");
                Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                startActivity(intent);
                Signup_Activity.this.finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Login_Activity.class);
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
            Intent intent = new Intent(this, Login_Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Toolbar Initialization
     */
    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.signup_toolbar);
        toolbar.setTitle(R.string.signup_toolbarText);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_toolbar_arrow);
    }

    public void signUp() {
        Log.d(TAG, "SignUp");

        if (!validate()) {
            onSignUpFailed();
            return;
        }

        btnSignUp.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(Signup_Activity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.signup_loaderText));
        progressDialog.show();

        // TODO: Implement your own authentication logic here.
        String userName, userPhoneNo, userKYCDocument, userEmail, userPassword, userPanNo = "",
                userAadharNo = "", userUpi, userType;
        userName = edTxtFirstName.getText().toString() + " " + edtTxtLastName.getText().toString();
        userPhoneNo = edTxtPhoneNo.getText().toString();
        userKYCDocument = spinKYCDoc.getSelectedItem().toString();
        userEmail = edTxtEmail.getText().toString();
        userPassword = edTxtPassword.getText().toString();
        userUpi = edTxtUpi.getText().toString();
        userType = spinUserType.getSelectedItem().toString();

        if (userKYCDocument.equalsIgnoreCase(Constants.kycDocumentsList[0])) {
            userPanNo = edTxtKYCNo.getText().toString();
        } else {
            userAadharNo = edTxtKYCNo.getText().toString();
        }

        User userDetails = new User(userName, userEmail, userPhoneNo, userPanNo, userAadharNo,
                userUpi, userType, userPassword);

        Log.d(TAG, "Post Register User: " + userName + " " + userPhoneNo + " " + userKYCDocument
                + " " + userPanNo + " " + userEmail + " " + userPassword + " " + userAadharNo + " "
                + userUpi);

        mP2PLendingAPIService = ApiClient.getP2PLendingAPIService();
        Call mCall = mP2PLendingAPIService.registerUser(userDetails);
        mCall.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.d(TAG, "Register User Response: " + response.body());
                JSONResponse jsonResponse = (JSONResponse) response.body();
                progressDialog.hide();
                onSignUpSuccess(jsonResponse);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                progressDialog.hide();
                onSignUpFailed();
                call.cancel();
            }
        });
    }

    /**
     * Validating all the fields on the signup page.
     *
     * @return
     */
    public boolean validate() {
        boolean valid = true;

        String userType = spinUserType.getSelectedItem().toString();
        String firstName = edTxtFirstName.getText().toString();
        String lastName = edTxtFirstName.getText().toString();
        String phoneNumber = edTxtPhoneNo.getText().toString();
        String kycDocument = spinKYCDoc.getSelectedItem().toString();
        String kycNumber = edTxtKYCNo.getText().toString();
        String email = edTxtEmail.getText().toString();
        String upi = edTxtUpi.getText().toString();
        String password = edTxtPassword.getText().toString();
        String rePassword = edTxtRePassword.getText().toString();

        // Email validation
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edTxtEmail.setError(getResources().getString(R.string.common_emailError));
            valid = false;
        } else {
            edTxtEmail.setError(null);
        }

        // Password validation
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            edTxtPassword.setError(getResources().getString(R.string.common_passwordError));
            valid = false;
        } else {
            edTxtPassword.setError(null);
        }

        // Re-Password validation
        if (rePassword.isEmpty() || !rePassword.equals(password)) {
            edTxtRePassword.setError(getResources().getString(R.string.signup_passwordConfirmError));
            valid = false;
        } else {
            edTxtRePassword.setError(null);
        }

        // User type validation
        if (userType.equals(getResources().getString(R.string.signup_spinnerUserTypeHint))) {
            activateError();
            valid = false;
        } else {
            toShowUserType = !toShowUserType;
            spinUserType.setError(null);
        }

        // KYC Document validation
        if (kycDocument.equals(getResources().getString(R.string.signup_spinnerKYCDocHint))) {
            activateError();
            valid = false;
        } else {
            toShowKYCDoc = !toShowKYCDoc;
            spinUserType.setError(null);
        }

        // KYC Document No. validation
        if (kycNumber.isEmpty()) {
            edTxtKYCNo.setError(getResources().getString(R.string.signup_kycNoError));
            valid = false;
        } else {
            edTxtKYCNo.setError(null);
        }

        // Phone number validation
        if (!android.util.Patterns.PHONE.matcher(phoneNumber).matches()) {
            edTxtPhoneNo.setError(getResources().getString(R.string.signup_phoneNoError));
            valid = false;
        } else {
            edTxtPhoneNo.setError(null);
        }

        // First Name validation
        if (firstName.isEmpty()) {
            edTxtFirstName.setError(getResources().getString(R.string.signup_firstNameError));
            valid = false;
        } else {
            edTxtFirstName.setError(null);
        }

        // Last Name validation
        if (lastName.isEmpty()) {
            edtTxtLastName.setError(getResources().getString(R.string.signup_lastNameError));
            valid = false;
        } else {
            edtTxtLastName.setError(null);
        }

        //UPI validation
        if (upi.isEmpty()) {
            edTxtUpi.setError(getResources().getString(R.string.signup_upiError));
            valid = false;
        } else {
            edTxtUpi.setError(null);
        }

        return valid;
    }

    /**
     * Method to display toast message for SignUp failure.
     */
    public void onSignUpFailed() {
        Toast.makeText(getBaseContext(), "SignUp failed", Toast.LENGTH_LONG).show();
        btnSignUp.setEnabled(true);
    }

    /**
     * Method for handling Success signup scenario.
     */
    public void onSignUpSuccess(JSONResponse response) {
        btnSignUp.setEnabled(true);
        Log.d(TAG, "Success Response: " + response);

        if (response.getResponseStatus() == Constants.CREATED) {
            Intent intent = new Intent(Signup_Activity.this,
                    Lendor_Dashboard_Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            Signup_Activity.this.finish();
        }
    }

    public void activateError() {
        if (!toShowKYCDoc) {
            spinKYCDoc.setError(getResources().getString(R.string.signup_spinnerKYCDocumentError));
        } else if (!toShowUserType) {
            spinUserType.setError(getResources().getString(R.string.signup_spinnerUserTypeError));
        } else {
            spinKYCDoc.setError(null);
            spinUserType.setError(null);
        }
    }
}