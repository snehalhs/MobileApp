package com.cs442.ssonawa1.myfoodorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class OrderSummary extends AppCompatActivity {
    ListView listView;
    Button button;
    ArrayAdapter<String> adapterItems;
    ArrayList<String> finalorder=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        ArrayList<String> arrlstSelItemStr = new ArrayList<String>();
        listView=(ListView)findViewById(R.id.orderlist);
        button=(Button)findViewById(R.id.backbtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DBHelper dbHelper=new DBHelper(getApplicationContext());
        finalorder=dbHelper.getOrder();
        if (finalorder.size()>0) {
            adapterItems = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_activated_1, finalorder);
            listView.setAdapter(adapterItems);
        }
        else
        {
            Toast toast= Toast.makeText(getApplicationContext(), "No Orders to display", Toast.LENGTH_SHORT);
            toast.show();
            this.finish();
        }

    }
}
