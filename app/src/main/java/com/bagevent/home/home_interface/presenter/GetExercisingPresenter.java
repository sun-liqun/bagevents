package com.bagevent.home.home_interface.presenter;

import android.content.Context;

import com.bagevent.home.data.ExercisingData;
import com.bagevent.home.home_interface.GetExercisingIn;
import com.bagevent.home.home_interface.OnGetExercisingIn;
import com.bagevent.home.home_interface.impls.GetExercisingImpls;
import com.bagevent.home.home_interface.view.GetExercisingView;

/**
 * Created by zwj on 2016/5/31.
 */
public class GetExercisingPresenter {
    private GetExercisingIn getExercising;
    private GetExercisingView getExercisingView;

    public GetExercisingPresenter(GetExercisingView getExercisingView) {
        this.getExercising = new GetExercisingImpls();
        this.getExercisingView = getExercisingView;
    }

    //获得活动列表
    public void getExercise(){
        getExercising.getExercising(getExercisingView.getContext(),getExercisingView.getUserId(),getExercisingView.getPage(),getExercisingView.getPageSize(),getExercisingView.getStatus(),getExercisingView.getShowType(), new OnGetExercisingIn() {
            @Override
            public void getSuccess(ExercisingData data) {
                //数据请求成功,隐藏没有活动的界面并显示活动列表
                getExercisingView.hideView();
                getExercisingView.showSuccess(data);
            }

//            @Override
//            public void getSuccess(String data) {
//                //数据请求成功,隐藏没有活动的界面并显示活动列表
//                getExercisingView.hideView();
//                getExercisingView.showSuccess(data);
//            }
            @Override
            public void getFailed(String errInfo) {
                //数据请求失败,展示错误信息并显示没有活动的界面
                getExercisingView.showFailed(errInfo);
                getExercisingView.showHideView();
            }

        });
    }
    //显示隐藏界面
    public void showHideView(){
        getExercisingView.showHideView();
    }
    //隐藏界面
    public void hideView(){
        getExercisingView.hideView();
    }
}
