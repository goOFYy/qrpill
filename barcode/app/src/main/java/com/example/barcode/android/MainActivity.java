package com.example.barcode.android;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barcode.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    static String idb;
    private DatabaseHelper mydb = new DatabaseHelper(this);
    private List<Item> itemList = new ArrayList<>();
    private TextView emptyTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton newBtn = (FloatingActionButton) findViewById(R.id.add);
        newBtn.setOnClickListener(this);;
        Log.d("t", "t");
        mydb = new DatabaseHelper(this);
        emptyTv = (TextView) findViewById(R.id.empty_text);

        RecyclerView itemsRv = (RecyclerView) findViewById(R.id.items_list);
        itemsRv.setLayoutManager(new LinearLayoutManager(this));
        viewAll();
        ItemsAdapter adapter = new ItemsAdapter(this, itemList, R.layout.item_row_layout);
        itemsRv.setAdapter(adapter);

        adapter.setOnItemClickListener(new ItemsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_scan) {
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                idb = null;
                Intent add_item = new Intent(this, AddItemActivity.class);
                startActivity(add_item);
                break;
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        // IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        // scanIntegrator.initiateScan();
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            idb = scanningResult.getContents();

            // Toast toast = Toast.makeText(getApplicationContext(),
            //       idb, Toast.LENGTH_SHORT);
            // toast.show();
            Log.d("test", "test");
            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    public void onBackPressed() {
        finish();
    }

    public void viewAll() {
        Cursor res = mydb.getalldata();
        emptyTv.setVisibility(res.getCount() == 0 ? View.VISIBLE : View.GONE);
        if (res.getCount() == 0) {
            return;
        }
        while (res.moveToNext()) {
            Item item = new Item();
            item.setId(res.getString(0));
            item.setBc(res.getString(1));
            item.setName(res.getString(2));
            item.setHours(res.getString(3));
            item.setNext(res.getString(4));
            itemList.add(item);
         }
    }


    public void showmsg(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();
    }
}

