package com.example.barcode.android;

import com.example.barcode.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.support.design.widget.Snackbar.LENGTH_LONG;
import static com.example.barcode.android.MainActivity.idb;
/**
 * Created by User on 4/17/2017.
 */

public class ScanActivity extends Activity {

    private TextView formatTxt;
    private TextView contentTxt;
    public String scanned;

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
            scanned=contentTxt.toString();
            DatabaseHelper mydb1 = new DatabaseHelper(this);
            Cursor res = mydb1.getdata(scanned);
            Item item = new Item();
            if (res.getCount() == 0) {
                return;
            }
            while (res.moveToNext()) {

                item.setId(res.getString(0));
                item.setBc(res.getString(1));
                item.setName(res.getString(2));
                item.setHours(res.getString(3));
                item.setNext(res.getString(4));
            }
            @SuppressLint("ResourceType") Snackbar mySnackbar = Snackbar.make(findViewById(R.layout.activity_scan), item.getName(), LENGTH_LONG);
            mySnackbar.show();
            Log.d("rytt", item.getName());
           // Intent main = new Intent(this, MainActivity.class);
            mydb1.close();
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    public void onBackPressed() {
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }
}
