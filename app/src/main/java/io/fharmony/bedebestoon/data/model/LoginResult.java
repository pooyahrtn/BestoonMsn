package io.fharmony.bedebestoon.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by parham on 2/1/17.
 */

public class LoginResult {

    @SerializedName("result")
    String result;
    @SerializedName("token")
    String token;

    public String getResult() {
        return result;
    }

    public String getToken() {
        return token;
    }
}
