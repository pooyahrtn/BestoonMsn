package io.fharmony.bedebestoon.data.http;

import android.os.AsyncTask;
import android.util.Log;

import com.afollestad.bridge.Bridge;
import com.afollestad.bridge.BridgeException;
import com.afollestad.bridge.Form;
import com.afollestad.bridge.Request;
import com.afollestad.bridge.Response;

import io.fharmony.bedebestoon.App;
import io.fharmony.bedebestoon.ui.activity.MainActivity;

/**
 * Created by parham on 2/1/17.
 */

public class InExAsync extends AsyncTask<Void, Void, Void> {

    String TAG = "Bridge";
    boolean isIncome = false;
    String text;
    String amount;
//    static boolean successful = false;

    public InExAsync(String amount, String text, boolean isIncome) {
        this.amount = amount;
        this.text = text;
        this.isIncome = isIncome;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        Request req = null;
        Response res = null;
        String urlComp = "expense/";
        if (isIncome) { urlComp = "income/";}
        Form form = new Form()
                .add("text", text)
                .add("amount", amount)
                .add("token", App.token);
        try {
            req = Bridge
                    .post("http://bestoon.ir/submit/" + urlComp)
                    .body(form)
                    .request();
            res = req.response();
//            if (res.isSuccess()) {
//                successful = true;
//            }
        }
        catch (BridgeException be) {
            Log.d(TAG, "doInBackground: something happened");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        MainActivity.refresh();
    }

}
