package com.example.barcode.android;

import com.example.barcode.R;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by User on 4/17/2017.
 */

public class ScanActivity extends Activity {

    private TextView formatTxt;
    private TextView contentTxt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        //scanBtn = (Button)findViewById(R.id.scan_button);
       formatTxt = (TextView)findViewById(R.id.scan_format);
        contentTxt = (TextView)findViewById(R.id.scan_content);
       // scanBtn.setOnClickListener(this);
    }

   // @Override
   // public void onClick(View v) {
       // if(v.getId()==R.id.scan_button){
          // IntentIntegrator scanIntegrator = new IntentIntegrator(this);
           // scanIntegrator.initiateScan();

       // }

   // }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        scanIntegrator.initiateScan();

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
//we have a result
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            formatTxt.setText("FORMAT: " + scanFormat);
            contentTxt.setText("CONTENT: " + scanContent);

            Intent main = new Intent(this, MainActivity.class);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
