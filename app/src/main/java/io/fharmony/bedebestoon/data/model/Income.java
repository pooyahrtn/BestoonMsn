package io.fharmony.bedebestoon.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by parham on 2/1/17.
 */

public class Income {
    @SerializedName("amount__sum")
    Double sum;
    @SerializedName("amount__count")
    long count;

    public Double getSum() {
        return sum;
    }

    public long getCount() {
        return count;
    }
}
