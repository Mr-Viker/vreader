package com.viker.android.vreader.activity;

import android.app.Fragment;

import com.viker.android.vreader.fragment.BookCityFragment;

/**
 * Created by Viker on 2016/5/21.
 */
public class BookCityActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new BookCityFragment();
    }


}
