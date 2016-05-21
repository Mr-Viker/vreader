package com.viker.android.vreader.activity;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Viker on 2016/5/21.
 * 作为所有Activity的基类，主要是为以后的重构做好铺垫。
 */
public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
