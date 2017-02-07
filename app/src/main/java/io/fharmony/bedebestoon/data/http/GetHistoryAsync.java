package io.fharmony.bedebestoon.data.http;

import android.content.Context;
import android.os.AsyncTask;

import io.fharmony.bedebestoon.ui.activity.MainActivity;

/**
 * Created by parham on 2/7/17.
 */

public class GetHistoryAsync extends AsyncTask<Void, Void, Void> {

    private Context context;
    static String result;

    public GetHistoryAsync(Context context) {
        this.context = context;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        //TODO : POST to /q/incomes and /q/expenses , process them to look good

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        MainActivity.showHistory(result, context);
    }
}
