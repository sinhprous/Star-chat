package com.example.sinh.starchat.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sinh.starchat.R;

public class RegisterActivity extends AppCompatActivity {

    UserRegisterTask mRegisterTask = null;
    EditText mNameView, mEmailView, mPassView, mPass2View, mPhoneView;
    Button mRegisButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Register form");
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        mNameView = (EditText) findViewById(R.id.register_name);
        mEmailView = (EditText) findViewById(R.id.register_email);
        mPassView = (EditText) findViewById(R.id.register_password);
        mPass2View = (EditText) findViewById(R.id.register_password_again);
        mPhoneView = (EditText) findViewById(R.id.register_phone);
        mRegisButton = (Button) findViewById(R.id.register_button);

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
        if (mRegisterTask != null) {
            return;
        }

        mNameView.setError(null);
        mEmailView.setError(null);
        mPassView.setError(null);
        mPass2View.setError(null);
        mPhoneView.setError(null);

        String name = mNameView.getText().toString();
        String email = mEmailView.getText().toString();
        String password = mPassView.getText().toString();
        String passwordAgain = mPass2View.getText().toString();
        String phone = mPhoneView.getText().toString();

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPassView.setError(getString(R.string.error_invalid_password));
        }

        // Check name
        if (!TextUtils.isEmpty(name) && !isNameValid(name)) {
            mNameView.setError("Name invalid");
        }

        // Check phone
        if (!TextUtils.isEmpty(phone) && !isPhoneValid(phone)) {
            mPhoneView.setError("Phone number invalid");
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
        }

        mRegisterTask = new UserRegisterTask(name, email, password, passwordAgain, phone);
        mRegisterTask.execute((Void) null);
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 4;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isNameValid(String name) {
        return true;
    }

    private boolean isPhoneValid(String name) {
        for (int i = 0; i < name.length(); ++i) if (!Character.isDigit(name.charAt(i))) return false;
        return true;
    }

    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {
        private final String mName;
        private final String mEmail;
        private final String mPassword;
        private final String mPassword2;
        private final String mPhone;

        UserRegisterTask(String name, String email, String password, String password2, String phone) {
            mName = name;
            mEmail = email;
            mPassword = password;
            mPassword2 = password2;
            mPhone = phone;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: Send json
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            // TODO: parse json
            super.onPostExecute(aBoolean);
            mRegisterTask = null;
        }

        @Override
        protected void onCancelled() {
            mRegisterTask = null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}
