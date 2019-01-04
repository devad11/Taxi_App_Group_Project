package com.taxiproject.group6.taxiapp.activities;

import android.content.Intent;
import android.media.audiofx.DynamicsProcessing;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.taxiproject.group6.taxiapp.R;
import com.taxiproject.group6.taxiapp.classes.ConfigPaypal;

import java.math.BigDecimal;

public class PaypalActivity extends AppCompatActivity {

    private static final int PAYPAL_REQUEST_CODE = 7171;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(ConfigPaypal.PAYPAL_CLIENT_ID);

    private Button btnPayNow;
    private EditText edtAmount;

    private String amount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal);

        btnPayNow = (Button) findViewById(R.id.btnPayNow);
        edtAmount = (EditText) findViewById(R.id.edtAmount);

        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processPayment();
            }
        });
    }

    private void processPayment() {
        amount = edtAmount.getText().toString();
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "EUR",
                "Payment", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }
}
