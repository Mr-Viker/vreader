package com.viker.android.vreader.activity;

import android.app.Fragment;
import android.util.Log;

import com.viker.android.vreader.fragment.SearchResultFragment;
import com.viker.android.vreader.modle.BookType;

/**
 * Created by Viker on 2016/5/22.
 */
public class SearchResultActivity extends SingleFragmentActivity {

    private static final String TAG = "SearchResultActivity";
    @Override
    protected Fragment createFragment() {
        BookType bookType = (BookType) getIntent().getSerializableExtra(
                SearchResultFragment.EXTRA_BOOK_TYPE);
        Log.d(TAG, "get BookType: " + bookType.getTypeName());
        return SearchResultFragment.newInstance(bookType);
    }

}
