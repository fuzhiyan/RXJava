package com.example.fzy.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by Administrator on 2017/10/17.
 */

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textView;
    private Button button;
    private final String TAG = "SecondActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        button = (Button) findViewById(R.id.second_btn);
        textView = (TextView) findViewById(R.id.second_text);
        button.setOnClickListener(this);
        Observable.just("Hello", "World", "付智焱", "目标薪资-14K")
                .map(new Func1<String, Integer>() {
                    //前一个参数是转换前的类型,后一个参数是转换后的类型
                    @Override
                    public Integer call(String s) {
                        Log.d(TAG, s);
                        return s.hashCode();
                    }
                })
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        Log.d(TAG, integer.intValue() + "");
                        return integer.intValue() + "";
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d(TAG, s);
                    }
                });
        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer number) {
                        Log.d(Thread.currentThread().getName(), "number:" + number);
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.second_btn:
                break;
        }
    }
}
