package com.rpqb.hackathon.p2plending.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rpqb.hackathon.p2plending.R;
import com.rpqb.hackathon.p2plending.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vikramv on 6/8/2017.
 */

public class Login_Activity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private static final int REQUEST_SIGNUP = 0;
    SharedPreferences sharedPreferences;

    @BindView(R.id.loginscreen_edTextEmail)
    EditText edtTxt_login;
    @BindView(R.id.loginscreen_edTextPassword)
    EditText edtTxt_Password;
    @BindView(R.id.loginscreen_btnLogin)
    Button btnLogin;
    @BindView(R.id.loginscreen_txtVwSignUp)
    TextView txtVwSignUp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences(Constants.AppSharedPreferences,
                Context.MODE_PRIVATE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        txtVwSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), Signup_Activity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                Login_Activity.this.finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the SplashScreen
        moveTaskToBack(true);
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        btnLogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(Login_Activity.this,
                R.style.AppTheme_Dark_Red_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.loginscreen_loaderText));
        progressDialog.show();

        String email = edtTxt_login.getText().toString();
        String password = edtTxt_Password.getText().toString();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userID", email);
        editor.commit();

        // TODO: Implement your own authentication logic here.
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void onLoginSuccess() {
        btnLogin.setEnabled(true);
        Intent intent = new Intent(Login_Activity.this,
                Borrower_Dashboard_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        Login_Activity.this.finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        btnLogin.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = edtTxt_login.getText().toString();
        String password = edtTxt_Password.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtTxt_login.setError(getResources().getString(R.string.common_emailError));
            valid = false;
        } else {
            edtTxt_login.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            edtTxt_Password.setError(getResources().getString(R.string.common_passwordError));
            valid = false;
        } else {
            edtTxt_Password.setError(null);
        }

        return valid;
    }
}