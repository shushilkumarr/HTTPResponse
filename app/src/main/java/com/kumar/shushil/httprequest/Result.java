package com.kumar.shushil.httprequest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static com.kumar.shushil.httprequest.R.id.result;

public class Result extends AppCompatActivity {
TextView resultText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        resultText= (TextView) findViewById(result);
        resultText.setText("Server Sent You \n\""+getIntent().getStringExtra("result")+"\"");
    }
}
