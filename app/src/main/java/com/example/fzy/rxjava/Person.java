package com.example.fzy.rxjava;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Administrator on 2017/10/17.
 */

public class Person implements Observer {

    //此方法 专门处理观察者变化后的。业务逻辑
    @Override
    public void update(Observable observable, Object o) {

        if (observable instanceof Account) {
            Account accout = (Account) observable;
            System.out.println(accout.moner);
        }

    }
}
