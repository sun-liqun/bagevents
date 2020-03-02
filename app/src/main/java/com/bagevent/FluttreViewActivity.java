package com.bagevent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

//import io.flutter.facade.Flutter;
//import io.flutter.plugin.common.EventChannel;
//import io.flutter.plugin.common.MethodCall;
//import io.flutter.plugin.common.MethodChannel;
//import io.flutter.view.FlutterMain;
//import io.flutter.view.FlutterView;

//public class FluttreViewActivity extends AppCompatActivity {
//    public static  String AndroidToFlutterCHANNEL1 = "com.pages.your/native_post";
//    public static  String AndroidToFlutterCHANNEL2 = "com.second.your/native_post";
//    public static  String FlutterToAndroidCHANNEL = "com.pages.your/native_get";
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        FlutterMain.startInitialization(this);
//        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_fluttre_view);
////
////        GeneratedPluginRegistrant.registerWith(this);
//
//        FlutterView flutterView = Flutter.createView(this,getLifecycle(), "route1");
//        FrameLayout.LayoutParams layout =new  FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
//        addContentView(flutterView, layout);
//
//        new EventChannel(flutterView,AndroidToFlutterCHANNEL1).setStreamHandler(
//                new EventChannel.StreamHandler() {
//                    @Override
//                    public void onListen(Object o, EventChannel.EventSink eventSink) {
//                        eventSink.success("来自Android原生的参数");
//                    }
//
//                    @Override
//                    public void onCancel(Object o) {
//                        Log.i("-----cal","000");
//                    }
//                }
//        );
//
//        new EventChannel(flutterView,AndroidToFlutterCHANNEL2).setStreamHandler(
//                new EventChannel.StreamHandler() {
//                    @Override
//                    public void onListen(Object o, EventChannel.EventSink eventSink) {
//                        eventSink.success(2);
//                    }
//
//                    @Override
//                    public void onCancel(Object o) {
//                        Log.i("-----cal","000");
//                    }
//                }
//        );
//
//        new MethodChannel(flutterView,FlutterToAndroidCHANNEL).setMethodCallHandler(
//                new MethodChannel.MethodCallHandler() {
//            @Override
//            public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
//                if (methodCall.method.equals("toNativeSomething")){
//                    result.success(1000);
//                }else if (methodCall.method.equals("toNativePush")){
//                    String text=methodCall.argument("title");
//                    Intent intent=new Intent(FluttreViewActivity.this,MyActivity.class);
//                    intent.putExtra("Test",text);
//                    startActivity(intent);
//                }else if (methodCall.method.equals("toNativePop")){
//                    String text=methodCall.argument("content");
//                    Intent intent=new Intent(FluttreViewActivity.this,MyActivity.class);
//                    intent.putExtra("Test",text);
//                    startActivity(intent);
//                }else {
//                    result.notImplemented();
//                }
//            }
//        });
//    }
//}
