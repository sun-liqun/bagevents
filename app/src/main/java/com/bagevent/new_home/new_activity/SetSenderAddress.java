package com.bagevent.new_home.new_activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.new_home.data.ProAndCity;
import com.bagevent.new_home.data.ProvinceBean;
import com.bagevent.util.AppManager;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.image_download.CityUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.pickerview.OptionsPickerView;
import com.xw.repo.XEditText;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZWJ on 2017/12/29 0029.
 */

public class SetSenderAddress extends BaseActivity {
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.ll_title_back)
    AutoLinearLayout llTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_select_address)
    TextView tvSelectAddress;
    @BindView(R.id.et_detail_address)
    XEditText etDetailAddress;

    private OptionsPickerView pvOptions;
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ProAndCity cityData;

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.layout_set_sender_address);
        ButterKnife.bind(this);
        initView();
        initData();
        getProCity();
    }


    private void initData() {
        String addressProvince = SharedPreferencesUtil.get(this,"expressProvince","");
        String addressCity = SharedPreferencesUtil.get(this,"expressCity","");
        String detailAddress = SharedPreferencesUtil.get(this,"expressDetailAddress","");
        if(!TextUtils.isEmpty(addressProvince) && !TextUtils.isEmpty(addressCity)) {
            tvSelectAddress.setText(addressProvince);
        }

        if(!TextUtils.isEmpty(detailAddress)) {
            etDetailAddress.setText(detailAddress);
        }
    }

    private void initView() {
        tvTitle.setText(R.string.set_express_address);
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
    }

    @OnClick({R.id.ll_title_back, R.id.tv_select_address,R.id.tv_confirm_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_title_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.tv_select_address:
                initCityWidget(tvSelectAddress);
                break;
            case R.id.tv_confirm_address:
                if(!TextUtils.isEmpty(tvSelectAddress.getText().toString())) {
                    SharedPreferencesUtil.put(this,"expressDetailAddress",etDetailAddress.getText().toString());
                    Toast.makeText(this,R.string.success_add,Toast.LENGTH_SHORT).show();
                    AppManager.getAppManager().finishActivity();
                }else {
                    Toast.makeText(this,R.string.select_province,Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 初始化省市选择器
     */
    private void initCityWidget(final TextView editText) {
        //选项选择器
        pvOptions = new OptionsPickerView(this);
        //三级联动效果
        pvOptions.setPicker(options1Items, options2Items, true);
        //设置选择的三级单位
        pvOptions.setLabels(" ", " ", " ");
        pvOptions.setTitle(getString(R.string.select_city));
        pvOptions.setCyclic(false, false, false);
        pvOptions.setCancelable(true);
        //设置默认选中的三级项目
        //监听确定选择按钮
        pvOptions.setSelectOptions(0, 0, 0);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                SharedPreferencesUtil.put(SetSenderAddress.this,"expressProvince",options1Items.get(options1).getPickerViewText());
                SharedPreferencesUtil.put(SetSenderAddress.this,"expressCity",options2Items.get(options1).get(option2));
                String tx = options1Items.get(options1).getPickerViewText()
                        + " " + options2Items.get(options1).get(option2);
                editText.setText(tx);
            }
        });
        pvOptions.show();
    }

    /**
     * 解析省市数据
     */
    private void getProCity() {
        String city = CityUtils.getCity();
        Gson gson = new Gson();
        cityData = gson.fromJson(city, ProAndCity.class);
        for (int i = 0; i < cityData.getProvinces().size(); i++) {
            ArrayList<String> citys = new ArrayList<>();
            ProAndCity.ProvincesBean temp = cityData.getProvinces().get(i);
            options1Items.add(new ProvinceBean(0, temp.getProvinceName()));
            for (int j = 0; j < temp.getCitys().size(); j++) {
                citys.add(temp.getCitys().get(j).getCitysName());
            }
            options2Items.add(citys);
        }
    }
}
