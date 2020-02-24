package com.bagevent.new_home.new_activity;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.CollectManagerFragment;


public class CollectorManagerActivity extends BaseActivity {

    FragmentManager fragmentManager;
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_collector_manager);
//        CollectManagerFragment fragment =new CollectManagerFragment();
//        Intent intent=getIntent();
//        Bundle bundle=intent.getExtras();
//        fragment.setArguments(bundle);
//
//        int eventId = bundle.getInt("eventId");
//         fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transactions = fragmentManager.beginTransaction();
//        transactions.add(R.id.al_container,fragment);
//        transactions.commit();
//
//    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.activity_collector_manager);
        CollectManagerFragment fragment =new CollectManagerFragment();
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        fragment.setArguments(bundle);

        int eventId = bundle.getInt("eventId");
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transactions = fragmentManager.beginTransaction();
        transactions.add(R.id.al_container,fragment);
        transactions.commit();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fragmentManager.popBackStack();
    }


}
