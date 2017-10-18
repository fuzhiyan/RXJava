package com.example.fzy.rxjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {

    private Button but;
    Subscriber<String> subscriber;
    Observable observable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        but = (Button) findViewById(R.id.main_btn);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });
        rxAndroidBase();
//        rxAndroidFrom();
//        rxAndroidJust();
        //筛选数据
//        rxAndroidFilterStream();
        //转换数据
//        rxAndroidMapStream();
        //拆分
//        rxAndroidFlatMap();
    }

    //拆分数据
    private void rxAndroidFlatMap() {
        //创建一个集合,并装上数据
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> integers1 = Arrays.asList(6, 7, 8);
        List<Integer> integers2 = Arrays.asList(9, 10);
        Observable.just(integers, integers1, integers2)//实现所有数据全部输出。而不是输出三个
                //相当于双层for循环，可以把即合理的所有数据都拿出来
                .flatMap(new Func1<List<Integer>, Observable<?>>() {
                    @Override
                    public Observable<?> call(List<Integer> integers) {
                        return Observable.from(integers);
                    }
                })
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        Log.d("flatMap", o + "");
                    }
                });
    }

    //数据转换
    private void rxAndroidMapStream() {
        Observable.just(1, 2, 3, 4, 5, 6)
                //第一个参数：原类型，第二个参数：转换后的数据类型
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return integer.toString();
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d("map", s);
                    }
                });
    }

    private void rxAndroidFilterStream() {

        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        Observable.from(list)
                //进行处理数据，大于2的拿出来，在下一个方法打印
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {

                        return integer > 2;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d("filter", integer + "");
                    }
                });

    }


    private void rxAndroidJust() {
        //just()里面的参数可以直接放一个集合。会自动 输出集合里的所以数字。参考上一个方法
        Observable.just(1, 2, 3, 4, 5)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d("just", integer + "");
                    }
                });

    }

    private void rxAndroidFrom() {
        //创建一个集合并创建数据
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        Observable.from(list)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {

                        Log.d("onNext", integer + "");
                    }
                });
    }

    private void rxAndroidBase() {

        //创建被观察者
        observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                //开始发送事件，执行的逻辑相当于set....
                subscriber.onNext("fzy");
                subscriber.onNext("gys");

            }
        });
        //创建观察者
        subscriber = new Subscriber<String>() {
            @Override//完成执行
            public void onCompleted() {

            }

            @Override//产生异常执行,与completed只执行一个
            public void onError(Throwable e) {

            }

            @Override//发生变化,相当于处理。。
            public void onNext(String s) {

                but.setText(s);
                Log.d("onNext", s + "天下第一");
            }
        };
        //二者关联
        observable.subscribe(subscriber);
    }

    //释放资源stop和destroy方法中都可以
    @Override
    protected void onStop() {
        super.onStop();
        if (subscriber != null & subscriber.isUnsubscribed()) {
            subscriber.unsubscribe();
        }
    }
}
