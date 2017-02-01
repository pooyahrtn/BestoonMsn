package io.fharmony.bedebestoon.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
    static SparkView sparkView;
    FloatingActionButton fab;
    Toolbar toolbar;
    Drawer drawer;
    static boolean isIncome = false;
    static Context context;
    static String text = null;
    static Double amount = 0d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (App.token == null) {
            finish();
            startActivity(new Intent(this, SignInUp.class));
        }
        toolbar = (Toolbar)findViewById(R.id.main_toolbar);
        inTimes = (TextView)findViewById(R.id.in_times);
        inAmount = (TextView)findViewById(R.id.in_comp);
        exTimes = (TextView)findViewById(R.id.ex_times);
        exAmount = (TextView)findViewById(R.id.ex_comp);
        sparkView = (SparkView)findViewById(R.id.main_sparkview);
        fab = (FloatingActionButton)findViewById(R.id.main_fab);
        context = this;

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

    public static void setTable(GenStatResult gsr) {
        inTimes.setText(String.valueOf(gsr.getIncome().getCount()));
        inAmount.setText(String.valueOf(gsr.getIncome().getSum()));
        exTimes.setText(String.valueOf(gsr.getExpense().getCount()));
        exAmount.setText(String.valueOf(gsr.getExpense().getSum()));

    }
    void setupDrawer() {
//        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.md_white_1000));
        AccountHeader header = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.colouredrect)
                .build();
        PrimaryDrawerItem newsItem = new PrimaryDrawerItem()
                .withName("تازه های بستون")
                .withSelectable(false)
                .withIdentifier(1);
        PrimaryDrawerItem signOutItem = new PrimaryDrawerItem()
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
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
    public static void refresh() {
        Log.d("bestoon", "refreshing... ");
        new GenStatAsync().execute();
    }


}
