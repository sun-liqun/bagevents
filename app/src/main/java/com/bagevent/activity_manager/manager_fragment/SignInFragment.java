package com.bagevent.activity_manager.manager_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bagevent.BaseFragment;
import com.bagevent.R;

/**
 * Created by zwj on 2016/5/30.
 *
 * 签到
 */
public class SignInFragment extends BaseFragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sign_in,container,false);
        return view;
    }
}
