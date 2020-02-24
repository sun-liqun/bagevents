package com.bagevent.new_home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bagevent.R;
import com.bagevent.new_home.adapter.GuestRankingAdapter;
import com.bagevent.new_home.new_activity.BaseActivity;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/21
 * <p>
 * desp 嘉宾排名页面
 * <p>
 * <p>
 * =============================================
 */
public class GuestRankingActivity extends BaseActivity {
    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.activity_guest_ranking);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        GuestRankingAdapter guestRankingAdapter = new GuestRankingAdapter(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(guestRankingAdapter);
    }
}
