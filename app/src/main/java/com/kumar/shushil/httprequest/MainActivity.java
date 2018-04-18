package com.kumar.shushil.httprequest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    EditText regno;
    Button submit;
    String macAddress, res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        regno = (EditText) findViewById(R.id.regno);
        submit = (Button) findViewById(R.id.submit);
        macAddress = getIntent().getStringExtra("mac");
        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if(regno.getText().toString().length()!=4) {
                    findViewById(R.id.error).setVisibility(View.VISIBLE);
                    return;
                }
                if(regno.getText().toString().length()==4)
                    findViewById(R.id.error).setVisibility(View.GONE);
                final ProgressDialog pd=new ProgressDialog(MainActivity.this);
                pd.setTitle("Please Wait...");
                pd.setMessage("fetching data..");
                pd.show();
                GetExample obj = new GetExample();
                res = null;
                try {
                    res = obj.execute("https://android-club-project.herokuapp.com/upload_details?reg_no=" + regno.getText().toString() + "&mac=" + macAddress).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(MainActivity.this, res, Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        Intent mainIntent = new Intent(MainActivity.this, Result.class);
                        mainIntent.putExtra("result", res);
                        MainActivity.this.startActivity(mainIntent);
                        MainActivity.this.finish();
                    }
                }, 2000);
            }
        });

    }

    public class GetExample extends AsyncTask<String, Void, String> {


        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {

            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(strings[0]);
            // replace with your url

            HttpResponse response;
            try {
                response = client.execute(request);
                BufferedReader r = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuilder total = new StringBuilder();

                String line = null;

                while ((line = r.readLine()) != null) {
                    total.append(line);
                }
                r.close();
                //return total.toString();
                Log.d("Response of GET request", total.toString());
                return total.toString();
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }

}
