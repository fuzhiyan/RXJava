package com.example.fzy.rxjava;

import java.util.Observable;

/**
 * Created by Administrator on 2017/10/17.
 */

public class Account extends Observable{

    public int moner=0;
    public void setMoney(int money){
        this.moner=money;
        setChanged();
        notifyObservers();
    }
}
