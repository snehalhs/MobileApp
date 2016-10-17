package com.cs442.ssonawa1.myfoodorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by sneha on 10/8/2016.
 */
public class OrderActivity extends Activity{
    public ArrayAdapter<String> adapterItems;
    public ListView listView;
    ArrayList<Item> selectedItems;
    double dblTotalValue=0;
    TextView tvtotal1;
    Button button;
    Activity cur;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_list);
        cur = this;
        Log.d("Create","creae");
        ArrayList<String> arrlstSelItemStr = new ArrayList<String>();
        tvtotal1=(TextView)findViewById(R.id.total1);
        listView=(ListView)findViewById(R.id.orderlist);
        button=(Button)findViewById(R.id.checkout);
        if (getIntent()!= null) {
            selectedItems = (ArrayList<Item>)getIntent().getSerializableExtra("selectedItems");
            arrlstSelItemStr = Item.getOrderedItems(selectedItems);
            adapterItems = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_activated_1, arrlstSelItemStr);
            dblTotalValue = getIntent().getDoubleExtra("total", 0);
            listView.setAdapter(adapterItems);
            tvtotal1.setText("Total: $"+ Double.toString(dblTotalValue));
        }
        else
        {
            selectedItems = null;
        }

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper=new DBHelper(getApplicationContext());

                int insert=dbHelper.insertOrder(selectedItems, dblTotalValue);
                if(insert>0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Your Order Checkout", Toast.LENGTH_SHORT);
                    toast.show();
                    //Intent intent=getIntent();
                    //intent.putExtra("modified_list",item);
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Error while inserting data, Unable to checkout", Toast.LENGTH_SHORT);
                    toast.show();
                    //Intent intent=getIntent();
                    //intent.putExtra("modified_list",item);
                   // Log.d("Count", Integer.toString(selectedItems.size()));

                }
                cur.setResult(1);
                selectedItems = null;
                cur.finish();
            }
        });
    }


}

 class DBHelper extends SQLiteOpenHelper{
     public static final String DATABASE_NAME = "OrderSummary.db";

     public DBHelper(Context context)
     {
         super(context, DATABASE_NAME , null, 1);
     }

     @Override
     public void onCreate(SQLiteDatabase db) {
         // TODO Auto-generated method stub
         db.execSQL(
                 "create table orderlist " +
                         "(order_id integer primary key, order_date_time DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                         "ordervalue real,ordered_item text)"
         );
     }

     @Override
     public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         // TODO Auto-generated method stub
         db.execSQL("DROP TABLE IF EXISTS orderlist");
         onCreate(db);
     }

     public int insertOrder(ArrayList<Item> list, double dblTotalValue){
         int orderid;
         String squery="select ifnull(max(ifnull(order_id,0)),0)+1 orderid from orderlist";
         SQLiteDatabase db=this.getWritableDatabase();
         Cursor cursor=db.rawQuery(squery,null);
         if(cursor!=null){
             cursor.moveToFirst();
             orderid=cursor.getInt(0);
         }
         else
         {
             orderid=-1;
         }
         cursor.close();
         db.close();
         if(orderid>0){
             String s="";
             for (int i=0; i<list.size();i++){
                    if(i<list.size()-1){
                        s=s+list.get(i).getId()+ ", ";
                    }
                 else{
                        s=s+list.get(i).getId();
                    }
             }
             ContentValues contentValues=new ContentValues();
             contentValues.put("order_id", orderid);
             contentValues.put("ordervalue", dblTotalValue );
             contentValues.put("ordered_item", s );
             db=this.getWritableDatabase();
             db.beginTransaction();
             try{
                 db.insert("orderlist", null, contentValues);
             }
             catch (Exception e){
                 Log.d("Error", "Error");
                 db.endTransaction();
                 db.close();
                 orderid=-2;
             }
             db.setTransactionSuccessful();
             db.endTransaction();
         }
         else{
             db.close();
         }
         db.close();
        return orderid;
     }


     public ArrayList<String> getOrder(){
         ArrayList<String> arrayList=new ArrayList<String>();
         String display="SELECT datetime(order_date_time, 'localtime') || ' $' || printf('%10.2f', ordervalue) || ' # '|| ordered_item as orddet from orderlist" +
                 " order by order_date_time desc";
         SQLiteDatabase db=this.getReadableDatabase();
         Cursor cursor=db.rawQuery(display, null);
         if(cursor!=null){
             cursor.moveToFirst();
             while(cursor.isAfterLast()==false){
                 arrayList.add(cursor.getString(0));
                 cursor.moveToNext();
             }
             db.close();

         }
         else{
             db.close();
         }
         return arrayList;
     }


}
