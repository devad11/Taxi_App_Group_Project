package com.taxiproject.group6.taxiapp.classes;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.taxiproject.group6.taxiapp.R;

public class PickerDialogObject extends Dialog implements
        android.view.View.OnClickListener {

    private Button homeButton, workButton, gpsLocButton;

    public PickerDialogObject(Activity activity) {
        super(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker_dialog);

        homeButton = findViewById(R.id.homeButton);
        workButton = findViewById(R.id.workButton);
        gpsLocButton = findViewById(R.id.gpsLocButton);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homeButton:

                break;
            case R.id.workButton:

                break;
            case R.id.gpsLocButton:

                break;
            default:
                break;
        }
        dismiss();
    }


}