package com.viker.android.vreader.activity;

import android.app.Fragment;

import com.viker.android.vreader.fragment.BookRackFragment;

/**
 * Created by Viker on 2016/5/21.
 * VReader的主页面，其托管碎片为BookRackFragment。
 */
public class BookRackActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new BookRackFragment();
    }

}
