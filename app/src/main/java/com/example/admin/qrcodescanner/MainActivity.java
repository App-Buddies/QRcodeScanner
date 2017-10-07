package com.example.admin.qrcodescanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonScan;
    private TextView textViewName, textViewAddress;
    // private IntentIntegrator qrScan;
    IntentIntegrator qrscan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //View objects
        buttonScan = (Button) findViewById(R.id.buttonScan);
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewAddress = (TextView) findViewById(R.id.textViewAddress);
        buttonScan.setOnClickListener(this);
        qrscan = new IntentIntegrator(this);
    }

    @Override
    public void onClick(View view) {
        qrscan.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getApplicationContext(), "no", Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONObject jsonObject = new JSONObject(intentResult.getContents());
                    textViewName.setText(jsonObject.getString("name"));
                    textViewAddress.setText(jsonObject.getString("address"));
                } catch (Exception e) {
                    Toast.makeText(this, intentResult.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
