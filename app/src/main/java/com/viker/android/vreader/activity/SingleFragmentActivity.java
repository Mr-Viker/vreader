package com.viker.android.vreader.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.viker.android.vreader.R;

/**
 * Created by Viker on 2016/5/21.
 * 作为单碎片活动的基类，所有只含有一个碎片的活动都应该继承此类。提高复用性与简洁代码
 */
public abstract class SingleFragmentActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlefragment);

        //获取碎片管理器和事务，方便管理托管碎片
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fragment=createFragment();
            fragmentTransaction.add(R.id.fragmentContainer, fragment);
            fragmentTransaction.commit();
        }
    }

    //将新建碎片的方式抽象出来，如此便可以复用此类作为单碎片活动的父类。
    protected abstract Fragment createFragment();


}
