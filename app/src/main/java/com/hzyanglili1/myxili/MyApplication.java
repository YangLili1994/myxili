package com.hzyanglili1.myxili;

import android.app.Application;

import com.hzyanglili1.myxili.utils.VolleyHelper;


/**
 * Created by Administrator on 2016/11/1.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //volley初始化
        VolleyHelper.getInstance().init(this);
    }
}
