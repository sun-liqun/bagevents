package com.bagevent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//import io.flutter.view.FlutterView;


public class MyActivity extends AppCompatActivity{
//
//    private TextView textView;
//    private FrameLayout frameLayout;
//    private FlutterView flutterView;

    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.tv_flutter)
    TextView tvFlutter;
//    public static  String AndroidToFlutterCHANNEL1 = "com.pages.your/native_post";
//    public static  String AndroidToFlutterCHANNEL2 = "com.second.your/native_post";
//    FlutterView flutterView;
    @OnClick(R.id.btn)
    public void click(){
//        flutterView = Flutter.createView(
//                MyActivity.this,getLifecycle(),"route1"
//        );
//        FrameLayout.LayoutParams layout =new  FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
//        addContentView(flutterView, layout);

//        Intent intent=new Intent(MyActivity.this, FluttreViewActivity.class);
//        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        ButterKnife.bind(this);

        String params =getIntent().getStringExtra("Test");
        if (!TextUtils.isEmpty(params)){
            tvFlutter.setText("这是flutter传回的值："+params);
        }
//        textView = findViewById(R.id.params);
//        frameLayout = findViewById(R.id.rl_flutter);
//        flutterView = Flutter.createView(this, getLifecycle(), "route");
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
//        frameLayout.addView(flutterView, layoutParams);

//        GeneratedPluginRegistrant.registerWith(this);

//        new EventChannel(flutterView,AndroidToFlutterCHANNEL1).setStreamHandler(
//                new EventChannel.StreamHandler() {
//                    @Override
//                    public void onListen(Object o, EventChannel.EventSink eventSink) {
//                        eventSink.success("来自android原生的参数");
//                    }
//
//                    @Override
//                    public void onCancel(Object o) {
//                        Log.i("-----cal","000");
//                    }
//                }
//        );

//        new EventChannel(flutterView,AndroidToFlutterCHANNEL2).setStreamHandler(
//                new EventChannel.StreamHandler() {
//                    @Override
//                    public void onListen(Object o, EventChannel.EventSink eventSink) {
//                        eventSink.success(100);
//                    }
//
//                    @Override
//                    public void onCancel(Object o) {
//                        Log.i("-----cal","000");
//                    }
//                }
//        );
//

//        new MethodChannel(getFlutterView(), "com.pages.your/native_get")
//                .setMethodCallHandler(new MethodChannel.MethodCallHandler() {
//                    @Override
//                    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
//                        if ("show".equals(methodCall.method)) {
//                            String text = methodCall.argument("text");
//                            int duration = methodCall.argument("duration");
//                            Toast.makeText(MyActivity.this, text, duration).show();
//                        }
//                    }
//                });

    }
}
