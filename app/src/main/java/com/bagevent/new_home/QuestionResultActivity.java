package com.bagevent.new_home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.new_home.adapter.QuestionResultAdapter;
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
 * desp 问卷结果页面
 * <p>
 * <p>
 * =============================================
 */
public class QuestionResultActivity extends BaseActivity {
    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.activity_question_result);

        ImageView iv_title_back = findViewById(R.id.iv_title_back);
        iv_title_back.setImageResource(R.drawable.fanhui);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText(R.string.questionnaire_results);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        QuestionResultAdapter questionResultAdapter = new QuestionResultAdapter();
        recyclerView.setAdapter(questionResultAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

    }
}
