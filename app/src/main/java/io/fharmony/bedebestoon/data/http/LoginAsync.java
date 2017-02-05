package io.fharmony.bedebestoon.data.http;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.afollestad.bridge.Bridge;
import com.afollestad.bridge.BridgeException;
import com.afollestad.bridge.Form;
import com.afollestad.bridge.Request;
import com.afollestad.bridge.Response;
import com.google.gson.Gson;

import io.fharmony.bedebestoon.App;
import io.fharmony.bedebestoon.data.model.LoginResult;
import io.fharmony.bedebestoon.ui.activity.MainActivity;
import io.fharmony.bedebestoon.ui.activity.SignInUp;

/**
 * Created by parham on 2/1/17.
 */

public class LoginAsync extends AsyncTask<Void, Void, Void> {

    String TAG = "bridge";
    String username;
    String password;
    SharedPreferences sp;
    Context context;

    public LoginAsync(String username, String password, SharedPreferences sp, Context context) {
        this.username = username;
        this.password = password;
        this.sp = sp;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        Request req = null;
        Response res = null;
        Gson gson = new Gson();
        Form form = new Form()
                .add("username", username)
                .add("password", password);

        try {
            req = Bridge
                    .post("http://bestoon.ir/accounts/login")
                    .body(form)
                    .request();
            res = req.response();
            if (res.isSuccess()) {
                LoginResult lr = gson.fromJson(res.asString(), LoginResult.class);
                if (lr.getResult().equals("ok")) {
                    sp.edit().putString(App.tokenKey, lr.getToken()).commit();
                    App.getToken(context);
                }
            }
        } catch (BridgeException be) {
            Log.d(TAG, "doInBackground: something happened");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        SignInUp.launchMain(context);
    }
}
