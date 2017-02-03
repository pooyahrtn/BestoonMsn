package io.fharmony.bedebestoon.data.http;

import android.os.AsyncTask;
import android.util.Log;

import com.afollestad.bridge.Bridge;
import com.afollestad.bridge.BridgeException;
import com.afollestad.bridge.Form;
import com.afollestad.bridge.Request;
import com.afollestad.bridge.Response;
import com.google.gson.Gson;

import io.fharmony.bedebestoon.App;
import io.fharmony.bedebestoon.data.model.GenStatResult;
import io.fharmony.bedebestoon.ui.activity.MainActivity;

/**
 * Created by parham on 2/1/17.
 */

public class GenStatAsync extends AsyncTask<Void, Void, Void> {

    String TAG = "Bridge";
    Response res = null;

    @Override
    protected Void doInBackground(Void... voids) {

        Request req = null;
        res = null;
        Form form = new Form()
                .add("token", App.token);
        try {
            req = Bridge
                    .post("http://bestoon.ir/q/generalstat")
                    .body(form)
                    .request();
            res = req.response();

        }
        catch (BridgeException be) {
            Log.d(TAG, "doInBackground: something happened");
            MainActivity.snackBarLauncher("یه چیزی شد!");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (res != null) {
            Gson gson = new Gson();
            MainActivity.setTable(gson.fromJson(res.asString(), GenStatResult.class));
        }
    }
}
