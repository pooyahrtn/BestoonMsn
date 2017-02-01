package io.fharmony.bedebestoon.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import io.fharmony.bedebestoon.App;
import io.fharmony.bedebestoon.R;
import io.fharmony.bedebestoon.data.http.LoginAsync;

public class SignInUp extends AppCompatActivity {

    EditText username;
    EditText password;
    Button signin;
    Button signup;
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_up);
        username = (EditText)findViewById(R.id.sign_username);
        password = (EditText)findViewById(R.id.sign_passwd);
        signin = (Button)findViewById(R.id.sign_signin);
        signup = (Button)findViewById(R.id.sign_signup);
        context = this;
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = context.getSharedPreferences("io.fharmony.bedebestoon",
                        Context.MODE_PRIVATE);
                new LoginAsync(username.getText().toString(),
                        password.getText().toString(),
                        sp).execute();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomTabsIntent ctIntent = new CustomTabsIntent.Builder()
                        .setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
                        .build();

                ctIntent.launchUrl(context, Uri.parse("http://bestoon.ir/accounts/register"));
            }
        });

    }

    public static void launchMain() {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
