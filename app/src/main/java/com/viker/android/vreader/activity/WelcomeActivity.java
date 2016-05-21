package com.viker.android.vreader.activity;

import android.app.Fragment;

import com.viker.android.vreader.fragment.WelcomeFragment;

/**
 * Created by Viker on 2016/5/21.
 * 进入app时最先展示的活动，其托管碎片为WelcomeFragment。
 */
public class WelcomeActivity extends SingleFragmentActivity {

    //定义该活动的托管Fragment
    @Override
    protected Fragment createFragment() {
        return new WelcomeFragment();
    }


}
