package com.cs442.ssonawa1.myfoodorder;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sneha on 10/1/2016.
 */
public class MenuFragment extends Fragment {
    public ArrayAdapter<Item> adapterItems;
    public ListView listView;
    //private OnListItemSelectedListener listener;
    ArrayList<Item> items;
    ArrayList<Item> selectedItems;
    double dblTotal = 0;
    TextView txtTotalPrice;
    boolean mDualPane;
    int mCurCheckPosition = 0;

    MenuDetailFragment mdf;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }




    @Override
    public void onCreate(Bundle savedInstanceState){
      super.onCreate(savedInstanceState);
        items=Item.getItems();
        adapterItems = new ArrayAdapter<Item>(getActivity(),
                android.R.layout.simple_list_item_activated_1, items);

        mdf = (MenuDetailFragment)
                getFragmentManager().findFragmentById(R.id.fragment_detail);

    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View detailsFrame = getActivity().findViewById(R.id.fragment_detail);
        mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;
        mdf = (MenuDetailFragment)
                getFragmentManager().findFragmentById(R.id.fragment_detail);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Item item=adapterItems.getItem(position);
                Log.d("Dual Pne",Boolean.toString(mDualPane));
                if (mDualPane) {

                    listView.setItemChecked(position, true);

                    mdf.modifyDetails(item.getTitle(), item.getDescription(), item.getPrice());

//
//                    MenuDetailFragment details = (MenuDetailFragment)
//                            getFragmentManager().findFragmentById(R.id.fragment_detail);
//                    //view=getActivity().getLayoutInflater().inflate(R.layout.frag_new, (ViewGroup) getView(),false);
//
//                    MenuDetailFragment f = new MenuDetailFragment();
//                    android.app.FragmentManager fm=getFragmentManager();
//                    android.app.FragmentTransaction ft = fm.beginTransaction();
//                    ft.replace(R.id.fragment_detail, f);
//                    ft.commit();
//                    //f.tvTitle = (TextView) view.findViewById(view.getId().);
//                    //f.tvTitle.setText(item.getTitle());
//                    //f.tvdesc.setText(item.getDescription());
//                    //f.tvBody.setText(item.getPrice());
//
//                    Bundle args = new Bundle();
                }
                else{
                    Intent i = new Intent(getActivity(), MenuDetailsActivity.class);
                    i.putExtra("item", item);
                    Log.d("hi clicked", "hi clicked");
                    startActivityForResult(i,0);
                }
            }});
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_list, container, false);
        listView=(ListView)view.findViewById(R.id.list);
        listView.setAdapter(adapterItems);
        txtTotalPrice=(TextView)view.findViewById(R.id.totalprice);
        txtTotalPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItems = new ArrayList<Item>();
                for(int i=0;i<items.size();i++){
                    if(items.get(i).getQuantity()!="0"){
                        selectedItems.add(items.get(i));
                        Log.d("selectedItems", selectedItems.get(selectedItems.size()-1).getTitle());

                    }
                }
                Intent intent=new Intent(getActivity(), OrderActivity.class);
                intent.putExtra("selectedItems", selectedItems);
                intent.putExtra("total", dblTotal);
                startActivityForResult(intent, 1);
            }
        });
        return view;
    }

    public void onActivityResult(int RequestCode, int ResultCode, Intent data){

        switch (RequestCode){
            case 0:
                if(ResultCode==1){
                    Item z= (Item) data.getSerializableExtra("modified_list");
                    String s=z.getQuantity();
                    dblTotal = 0;
                    for (int i=0;i<items.size();i++)
                    {
                        if (items.get(i).getId().equals(z.getId()))
                        {
                            items.get(i).setQuantity(s);
                        }

                        dblTotal = dblTotal + (Double.parseDouble(items.get(i).getQuantity())
                                * Double.parseDouble(items.get(i).getPrice()));
                    }
                    Log.d("s", s);
                    adapterItems.notifyDataSetChanged();
                    txtTotalPrice.setText("Total: $ "+Double.toString(dblTotal));
                }
                break;

            case 1:
                if (ResultCode==1)
                {
                    for(int i=0;i<items.size();i++){
                        items.get(i).setQuantity("0");
                    }
                    dblTotal=0;
                    adapterItems.notifyDataSetChanged();
                    txtTotalPrice.setText("Total: $ "+Double.toString(dblTotal));
                }
        }

    }

    public void setActivateOnItemClick(boolean activateOnItemClick) {
        listView.setChoiceMode(
                activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
                        : ListView.CHOICE_MODE_NONE);
    }

    public void updateAdapter (ArrayList<Item> arrlstItems)
    {
        if (arrlstItems!=null) {
            items = arrlstItems;
            adapterItems.notifyDataSetChanged();
        }
    }

}
