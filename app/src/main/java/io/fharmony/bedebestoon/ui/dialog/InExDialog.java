package io.fharmony.bedebestoon.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import io.fharmony.bedebestoon.R;
import io.fharmony.bedebestoon.data.http.InExAsync;

/**
 * Created by parham on 2/2/17.
 */

public class InExDialog extends DialogFragment {

    static boolean isIncome = true;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.in_ex_dialog, container, false );
        getDialog().setTitle("گزارش");
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button submit = (Button)view.findViewById(R.id.dialog_submit);
        final RadioGroup inOrEx = (RadioGroup)view.findViewById(R.id.dialog_radio_group);
        inOrEx.check(R.id.dialog_in_btn);

        final EditText text = (EditText)view.findViewById(R.id.dialog_text);
        final EditText amount = (EditText)view.findViewById(R.id.dialog_amount);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inOrEx.getCheckedRadioButtonId() ==
                        R.id.dialog_in_btn) {
                    isIncome = true;
                }
                else {
                    isIncome = false;
                }
                new InExAsync(amount.getText().toString(),
                        text.getText().toString(),
                        isIncome)
                        .execute();
                dismiss();
            }
        });
        text.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }
}
