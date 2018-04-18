package com.kumar.shushil.httprequest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class Mac_add extends AppCompatActivity {
    Button fetch;
    TextView mac;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mac_add);
        fetch = (Button) findViewById(R.id.fetchbtn);
        mac = (TextView) findViewById(R.id.mac);
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    fetch.setVisibility(View.GONE);
//                     ProgressDialog pd=new ProgressDialog(getApplicationContext());
//                     pd.setTitle("Please wait");
//                     pd.setMessage("fetching address...");
//                     pd.show();
                    final ProgressDialog progress = new ProgressDialog(Mac_add.this);
                    progress.setTitle("Please Wait");
                    progress.setMessage("Fetching address...");
                    progress.show();

                    Runnable progressRunnable = new Runnable() {

                        @Override
                        public void run() {
                            progress.cancel();
                            try {
                                List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
                                for (NetworkInterface nif : all) {
                                    if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                                    byte[] macBytes = nif.getHardwareAddress();
                                    if (macBytes == null) {
                                        Toast.makeText(Mac_add.this, "NA", Toast.LENGTH_SHORT).show();
                                    }

                                    final StringBuilder res1 = new StringBuilder();
                                    for (byte b : macBytes) {
                                        //res1.append(Integer.toHexString(b & 0xFF) + ":");
                                        res1.append(String.format("%02X:", b));
                                    }

                                    if (res1.length() > 0) {
                                        res1.deleteCharAt(res1.length() - 1);
                                    }

                                    mac.setText("Mac: \n\n" + res1);
                                    mac.setVisibility(View.VISIBLE);
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent mainIntent = new Intent(Mac_add.this, MainActivity.class);
                                            mainIntent.putExtra("mac",res1.toString());
                                            Mac_add.this.startActivity(mainIntent);
                                            Mac_add.this.finish();
                                        }
                                    }, 2000);
                                }
                            } catch (Exception ex) {
                            }
                        }
                    };

                    Handler pdCanceller = new Handler();
                    pdCanceller.postDelayed(progressRunnable, 3000);

                }
            }
        });
    }
}
