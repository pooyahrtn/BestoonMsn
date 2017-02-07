package io.fharmony.bedebestoon.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import io.fharmony.bedebestoon.App;
import io.fharmony.bedebestoon.R;
import io.fharmony.bedebestoon.data.http.LoginAsync;

public class SignInUp extends AppCompatActivity {

    TextView title;
    EditText username;
    EditText password;
    Button signin;
    Button signup;
    Context context;
    Typeface vazir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_up);
        AssetManager am = getApplicationContext().getAssets();
        vazir = Typeface.createFromAsset(am, "fonts/Vazir.ttf");
        title = (TextView)findViewById(R.id.sign_title);
        username = (EditText)findViewById(R.id.sign_username);
        password = (EditText)findViewById(R.id.sign_passwd);
        signin = (Button)findViewById(R.id.sign_signin);
        signup = (Button)findViewById(R.id.sign_signup);
        context = this;
        setTypeface();

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_GO) {
                    signin();
                    return true;
                }
                return false;
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               signin();
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

    public static void launchMain(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    void setTypeface() {
        signin.setTypeface(vazir);
        signup.setTypeface(vazir);
        title.setTypeface(vazir);
    }
    void signin() {
        title.setText("در حال ورود...");
        SharedPreferences sp = context.getSharedPreferences("io.fharmony.bedebestoon",
                Context.MODE_PRIVATE);
        new LoginAsync(username.getText().toString(),
                password.getText().toString(),
                sp,
                context).execute();
    }


}
