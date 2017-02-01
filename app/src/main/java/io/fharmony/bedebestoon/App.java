package io.fharmony.bedebestoon;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by parham on 2/1/17.
 */

public class App extends Application {

    public static String token = null;
    public static String tokenKey = "io.fharmony.bedebestoon.token";
    static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        getToken();
    }
    public static void getToken() {
        SharedPreferences prefs = context.getSharedPreferences(
                "io.fharmony.bedebestoon", Context.MODE_PRIVATE);
        token = prefs.getString(tokenKey, null);
    }
}
