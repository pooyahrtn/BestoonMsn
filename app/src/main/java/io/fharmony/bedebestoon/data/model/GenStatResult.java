package io.fharmony.bedebestoon.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by parham on 2/1/17.
 */

public class GenStatResult {
    @SerializedName("income")
    Income income;
    @SerializedName("expense")
    Expense expense;

    public Expense getExpense() {
        return expense;
    }

    public Income getIncome() {
        return income;
    }
}
