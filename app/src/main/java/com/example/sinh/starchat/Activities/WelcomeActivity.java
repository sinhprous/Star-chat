package com.example.sinh.starchat.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.sinh.starchat.R;

public class WelcomeActivity extends AppCompatActivity {

    TextView welcomeText;
    Button acceptButton;
    CheckBox checkRemeber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_welcome);

        getActionBar().setTitle("Welcome");

        welcomeText = (TextView) findViewById(R.id.welcome_text);
        Intent intent = getIntent();
        // TODO: add license ...
        welcomeText.setText("Welcome "+intent.getStringExtra("name"));

        checkRemeber = (CheckBox) findViewById(R.id.checkRemember);

        acceptButton = (Button) findViewById(R.id.welcome_accept_button);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.CURRENT_USER_PREF, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (checkRemeber.isChecked()) {
                    editor.putBoolean(LoginActivity.REMEMBER_INFO_KEY, true);
                    editor.commit();
                } else {
                    editor.remove(LoginActivity.REMEMBER_INFO_KEY);
                    editor.commit();
                }
                startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
            }
        });
    }
}
