package com.example.barcode.android;

import android.content.Intent;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.barcode.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.barcode.android.MainActivity.idb;

public class AddItemActivity extends AppCompatActivity implements View.OnClickListener {
    private Button addBtn;
    private String med;
    private String hour;
    private String bc;

    EditText tmp1;
    EditText tmp2;
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
        addBtn = (Button) findViewById(R.id.Addmed);
        tmp1 = (EditText) findViewById(R.id.medicine);

        tmp2 = (EditText) findViewById(R.id.hours);
        addBtn.setOnClickListener(this);

        if (idb == null) {

            IntentIntegrator scanIntegrator1 = new IntentIntegrator(this);
            scanIntegrator1.initiateScan();
        }


    }

    @Override
    public void onClick(View v) {

        String in="{\n" +
                "  \"bc\": \"123545\",\n" +
                "  \"medname\": \"qrtesto\",\n" +
                "  \"hour\": 4\n" +
                "}";
        try {
            JSONObject root = new JSONObject(idb);
            bc=root.getString("bc");
            med= root.getString("medname");
            hour =root.getString("hour");
            Log.d("med=",med);
            Log.d("hour=",hour);
            DatabaseHelper mydb1 = new DatabaseHelper(this);

            String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US).format(new Date());
            Log.d(med, timeStamp);
            boolean isinserted = mydb1.insertData( bc, med, hour, timeStamp);
            if (isinserted) {
                Intent main = new Intent(this, MainActivity.class);
                startActivity(main);

            } else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "No!", Toast.LENGTH_SHORT);
                toast.show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
  /*  @Override
    public void onClick(View v) {

        if (v.getId() == R.id.Addmed) {
            med = tmp1.getText().toString();
            hour = tmp2.getText().toString();
            if (med.equals("") || hour.equals(""))
                Snackbar.make(v, "Please fill all the fields", BaseTransientBottomBar.LENGTH_SHORT).show();
            else {
                DatabaseHelper mydb1 = new DatabaseHelper(this);
                Log.d(med, hour);
                String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US).format(new Date());
                Log.d(med, timeStamp);
                boolean isinserted = mydb1.insertData(idb, med, hour, timeStamp);
                if (isinserted) {
                    Intent main = new Intent(this, MainActivity.class);
                    startActivity(main);

                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "No!", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        }
    }*/

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Intent add = new Intent(this, AddItemActivity.class);
        IntentIntegrator scanIntegrator1 = new IntentIntegrator(this);
        scanIntegrator1.initiateScan();
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            idb = scanningResult.getContents();
            Log.d("resu",idb);
            Intent myIntent1 = new Intent(this, AddItemActivity.class);
            startActivity(myIntent1);

        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void insert_data() {

    }

    @Override
    public void onBackPressed() {
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }

}
