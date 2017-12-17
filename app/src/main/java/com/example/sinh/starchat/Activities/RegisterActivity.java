package com.example.sinh.starchat.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sinh.starchat.JsonParsers.APIService;
import com.example.sinh.starchat.JsonParsers.ApiUtils;
import com.example.sinh.starchat.Model.User;
import com.example.sinh.starchat.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    APIService apiService = null;
    EditText mNameView, mEmailView, mPassView, mPass2View, mPhoneView;
    private View mProgressView;
    private View mRegisterFormView;
    Button mRegisButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Register");
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        mNameView = (EditText) findViewById(R.id.register_name);
        mEmailView = (EditText) findViewById(R.id.register_email);
        mPassView = (EditText) findViewById(R.id.register_password);
        mPass2View = (EditText) findViewById(R.id.register_password_again);
        mPhoneView = (EditText) findViewById(R.id.register_phone);
        mRegisButton = (Button) findViewById(R.id.register_button);
        mRegisterFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);

        mPass2View.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pass = mPassView.getText().toString();
                if (pass == null) return;
                s = s.toString();
                if (!pass.equals(s)) {
                    //Log.d("prous", s+", "+pass);
                    mPass2View.setError("Passwords must be the same!");
                }
                else {
                    //Log.d("prous", s+", "+pass+"true");
                    mPass2View.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mRegisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });
    }

    private void attemptRegister() {
        mNameView.setError(null);
        mEmailView.setError(null);
        mPassView.setError(null);
        mPass2View.setError(null);
        mPhoneView.setError(null);

        final String name = mNameView.getText().toString();
        final String email = mEmailView.getText().toString();
        final String password = mPassView.getText().toString();
        String passwordAgain = mPass2View.getText().toString();
        String phone = mPhoneView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (password.equals(passwordAgain)) {
            if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
                mPassView.setError(getString(R.string.error_invalid_password));
                focusView = mPassView;
                cancel = true;
            }
        } else {
            mPass2View.setError("Passwords must be the same!");
            focusView = mPass2View;
            cancel = true;
        }

        // Check name
        if (!TextUtils.isEmpty(name) && !isNameValid(name)) {
            mNameView.setError("Name invalid");
            focusView = mNameView;
            cancel = true;
        }

        // Check phone
        if (!TextUtils.isEmpty(phone) && !isPhoneValid(phone)) {
            mPhoneView.setError("Phone number invalid");
            focusView = mPhoneView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            apiService = ApiUtils.getAPIService();
            apiService.register(new User(name, email, password)).enqueue(new Callback<APIService.RegisterResponse>() {
                @Override
                public void onResponse(Call<APIService.RegisterResponse> call, Response<APIService.RegisterResponse> response) {
                    showProgress(false);
                    if (response.isSuccessful()) {
                        String res = response.body().toString();
                        if (res != null && res.equals("false")) {
                            mPassView.requestFocus();
                            mPassView.setError(getString(R.string.error_incorrect_password));
                        } else if (res != null && res.equals("true")){
                            // save current info
                            SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.CURRENT_USER_PREF, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString(LoginActivity.CURRENT_USER_MAIL_KEY, email);
                            editor.putString(LoginActivity.CURRENT_USER_PASS_KEY, password);
                            editor.commit();

                            // start activity
                            Intent intent = new Intent(RegisterActivity.this, WelcomeActivity.class);
                            intent.putExtra("name", name);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getBaseContext(), "SERVER ERROR", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "ERROR CODE "+response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<APIService.RegisterResponse> call, Throwable t) {
                    Toast.makeText(getBaseContext(), "CONNECT ERROR", Toast.LENGTH_SHORT).show();
                    showProgress(false);
                }
            });
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 4;
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isNameValid(String name) {
        return true;
    }

    private boolean isPhoneValid(String name) {
        for (int i = 0; i < name.length(); ++i) if (!Character.isDigit(name.charAt(i))) return false;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}
