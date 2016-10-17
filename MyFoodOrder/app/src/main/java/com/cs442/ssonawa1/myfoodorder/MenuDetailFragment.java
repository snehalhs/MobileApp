package com.cs442.ssonawa1.myfoodorder;


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

/**
 * Created by sneha on 10/1/2016.
 */
public class MenuDetailFragment extends Fragment implements View.OnClickListener {
    private Item item;
    Button btn;
    EditText tvquant;
    private View view;
    TextView tvTitle, tvBody, tvId, tvdesc;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (getArguments()!= null) {
            item = (Item) getArguments().getSerializable("item");
        }
        else
        {
            item = null;
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view=inflater.inflate(R.layout.frag_new, container,false);
         tvTitle = (TextView) view.findViewById(R.id.title);
         //tvBody = (TextView) view.findViewById(R.id.price);
         //tvId=(TextView)view.findViewById(R.id.idtitle);
         tvdesc=(TextView)view.findViewById(R.id.description);
        tvquant=(EditText)view.findViewById(R.id.editquant);
        btn=(Button)view.findViewById(R.id.backbutton);
        if (item!= null) {
            tvTitle.setText(item.getId()+". "+item.getTitle()+" $ "+item.getPrice());
            //tvBody.setText("$ " + item.getPrice());
            //tvId.setText(item.getId() + ".");
            tvdesc.setText(item.getDescription());
            tvquant.setText("0");
            btn.setOnClickListener(this);
        }
        return view;
    }
   @Override
    public void onClick(View v) {
       Log.d("Hi","Hi");
       int quantity=0;
       if (tvquant.getText().equals("")==false) {
           quantity = Integer.parseInt(tvquant.getText().toString());
       }
       item.setQuantity(Integer.toString(quantity));
       Intent intent=this.getActivity().getIntent();
       intent.putExtra("modified_list",item);
       this.getActivity().setResult(1,intent);
       this.getActivity().finish();
       Log.d("Hello","Hello");
       Log.d("quantity", Integer.toString(quantity));
        }


    public static MenuDetailFragment newInstance(Item item) {
        MenuDetailFragment fragmentDemo = new MenuDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("item", item);
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }

    public void modifyDetails(String title, String description, String price) {
        tvTitle.setText(title);
        tvdesc.setText(description);
    }

}
