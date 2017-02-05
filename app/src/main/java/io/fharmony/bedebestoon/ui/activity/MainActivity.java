package io.fharmony.bedebestoon.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.robinhood.spark.SparkView;

import java.util.ArrayList;

import io.fharmony.bedebestoon.App;
import io.fharmony.bedebestoon.R;
import io.fharmony.bedebestoon.data.http.GenStatAsync;
import io.fharmony.bedebestoon.data.http.InExAsync;
import io.fharmony.bedebestoon.data.model.GenStatResult;
import io.fharmony.bedebestoon.ui.dialog.InExDialog;

public class MainActivity extends AppCompatActivity {

    static TextView inTimes;
    static TextView inAmount;
    static TextView exTimes;
    static TextView exAmount;
    static TextView wholeAmount;
    static SparkView sparkView;
    static CoordinatorLayout mainCoord;
    FloatingActionButton fab;
    Toolbar toolbar;
    Drawer drawer;
    Context context;
    static Typeface vazir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (App.token == null) {
            finish();
            startActivity(new Intent(this, SignInUp.class));
        }
        AssetManager am = getApplicationContext().getAssets();
        vazir = Typeface.createFromAsset(am, "fonts/Vazir.ttf");
        toolbar = (Toolbar)findViewById(R.id.main_toolbar);
        inTimes = (TextView)findViewById(R.id.in_times);
        inAmount = (TextView)findViewById(R.id.in_comp);
        exTimes = (TextView)findViewById(R.id.ex_times);
        exAmount = (TextView)findViewById(R.id.ex_comp);
        wholeAmount = (TextView)findViewById(R.id.comp);
        sparkView = (SparkView)findViewById(R.id.main_sparkview);
        fab = (FloatingActionButton)findViewById(R.id.main_fab);
        mainCoord = (CoordinatorLayout)findViewById(R.id.activity_main);
        context = this;

        setTypeface();
        setSupportActionBar(toolbar);
        setupDrawer();
        setupFab();
        if (App.token != null) {
            refresh();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        refresh();
        return true;
    }

    void setTypeface() {
        //there really should be a better way
        TextView tv8 = (TextView)findViewById(R.id.textView8);
        TextView tv3 = (TextView)findViewById(R.id.textView3);
        TextView tv5 = (TextView)findViewById(R.id.textView5);
        TextView tv6 = (TextView)findViewById(R.id.textView6);

        tv3.setTypeface(vazir);tv5.setTypeface(vazir);tv6.setTypeface(vazir);
        tv8.setTypeface(vazir);inAmount.setTypeface(vazir);inTimes.setTypeface(vazir);
        exAmount.setTypeface(vazir);exTimes.setTypeface(vazir);wholeAmount.setTypeface(vazir);
    }

    public static void setTable(GenStatResult gsr) {
        inTimes.setText(latinToArabicNum(String.valueOf(gsr.getIncome().getCount())));
        if (gsr.getIncome().getSum() != null) {
            inAmount.setText(latinToArabicNum(String.valueOf(gsr.getIncome().getSum())));
        }
        else {
            inAmount.setText("۰");
        }
        exTimes.setText(latinToArabicNum(String.valueOf(gsr.getExpense().getCount())));
        if (gsr.getExpense().getSum() != null) {
            exAmount.setText(latinToArabicNum(String.valueOf(gsr.getExpense().getSum())));
        }
        else {
            exAmount.setText("۰");
        }
        wholeAmount.setText("۰");
        Double wholeAmountD = gsr.getIncome().getSum() - gsr.getExpense().getSum();
        wholeAmount.setText(latinToArabicNum(wholeAmountD.toString()));



    }
    void setupDrawer() {
//        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.md_white_1000));
        AccountHeader header = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.colouredrect)
                .withTypeface(vazir)
                .build();
        PrimaryDrawerItem newsItem = new PrimaryDrawerItem()
                .withName("تازه های بستون")
                .withTypeface(vazir)
                .withSelectable(false)
                .withIdentifier(1);
        PrimaryDrawerItem signOutItem = new PrimaryDrawerItem()
                .withTypeface(vazir)
                .withName("خروج")
                .withSelectable(false)
                .withIdentifier(2);

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withDrawerGravity(GravityCompat.END)
                .withToolbar(toolbar)
                .addDrawerItems(newsItem, signOutItem)
                .withSelectedItem(-1)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        int id = (int)drawerItem.getIdentifier();
                        if (id == 1) {
                            //later i guess?
                        }
                        else if (id == 2) {
                            signOut();
                        }
                        return true;
                    }
                })
                .withAccountHeader(header)
                .build();


    }
    void setupFab() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                InExDialog dialog = new InExDialog();
                dialog.show(fm, "tagMsn");
            }
        });
    }
    void signOut() {
        SharedPreferences sp = this.getSharedPreferences("io.fharmony.bedebestoon", Context.MODE_PRIVATE);
        sp.edit().remove(App.tokenKey).apply();
        App.getToken(this);
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
    public static void refresh() {
        Log.d("bestoon", "refreshing... ");
        new GenStatAsync().execute();
    }
    public static void snackBarLauncher(String text) {
//        Snackbar.make(mainCoord, text, 1000).show();
        Snackbar errSnack = Snackbar.make(mainCoord, text, Snackbar.LENGTH_SHORT);
        TextView tv = (TextView)errSnack.getView().findViewById(android.support.design.R.id.snackbar_text);
        tv.setTypeface(vazir);

    }
    public static void setupGraph() {
        //not sure if even need this
    }
    static String latinToArabicNum(String str) {
        char[] arabicChars = {'٠','١','٢','٣','٤','٥','٦','٧','٨','٩'};
        StringBuilder builder = new StringBuilder();
        for(int i =0;i<str.length();i++)
        {
            if(Character.isDigit(str.charAt(i)))
            {
                builder.append(arabicChars[(int)(str.charAt(i))-48]);
            }
            else
            {
                builder.append(str.charAt(i));
            }
        }

        return builder.toString();
    }


}
