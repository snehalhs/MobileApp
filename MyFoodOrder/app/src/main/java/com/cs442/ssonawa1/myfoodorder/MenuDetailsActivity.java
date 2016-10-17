package com.cs442.ssonawa1.myfoodorder;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;



/**
 * Created by sneha on 10/1/2016.
 */
public class MenuDetailsActivity extends Activity {
    MenuDetailFragment fragmentItemDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail);

        Item item = (Item) getIntent().getSerializableExtra("item");
        if (savedInstanceState == null) {

            fragmentItemDetail = MenuDetailFragment.newInstance(item);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.details, fragmentItemDetail);
            ft.commit();
        }
    }

}
