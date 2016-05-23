package com.viker.android.vreader.fragment;

import android.app.Fragment;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.viker.android.vreader.R;
import com.viker.android.vreader.database.VRDatabase;
import com.viker.android.vreader.modle.Book;
import com.viker.android.vreader.modle.BookType;
import com.viker.android.vreader.util.JsonParserUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Viker on 2016/5/22.
 */
public class SearchResultFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "SearchResultFragment";
    public static final String EXTRA_BOOK_TYPE = "bookType";

    private BookType bookType; //从Intent中传入的BookType实例，用于查询操作。

    public List<Book> bookList;
    private BookAdapter adapter; //自定义的适配器，用于显示书籍类目

    public VRDatabase vrDatabase;

    private Button btnBack; //返回键
    private Button btnSearch; //搜索键
    private ListView lvBook;

    /*将SearchResultFragment()构造方法进行封装，如此便可以获得托管Activity要传入的参数，但又不会
    *被某一个特定托管Activity私有化[因为不会改变SearchResultFragment()这个构造方法，该方法还是可以用]。
    *此方法有点类似于SearchResultFragment(BookType bookType)构造方法。这样做可以让某些不需要传
    *入参数的Activity还是可以直接使用SearchResultFragment()构造方法。*/
    public static SearchResultFragment newInstance(BookType bookType) {
        SearchResultFragment searchResultFragment = new SearchResultFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_BOOK_TYPE, bookType);
        searchResultFragment.setArguments(args);
        return searchResultFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() execute");
        //获得newInstance方法中向Bundle中添加的数据
        bookType = (BookType) getArguments().getSerializable(EXTRA_BOOK_TYPE);
        Log.d(TAG, "get BookType: " + bookType);

        vrDatabase = VRDatabase.getVrDatabase(getActivity());
        //创建新线程发送网络请求。
        new SearchResultHttpTask().execute(bookType);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchresult, container, false);
        btnBack = (Button) view.findViewById(R.id.btn_searchresult_back);
        btnSearch = (Button) view.findViewById(R.id.btn_searchresult_search);
        lvBook = (ListView) view.findViewById(R.id.lv_searchresult_book);
        //注册各组件的点击事件。
        btnBack.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

        return view;
    }

    //响应标题栏点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_searchresult_back: //返回键
                getActivity().finish();
                break;

            case R.id.btn_searchresult_search: //搜索键

                break;

            default:
                break;
        }
    }


    /*建立内部类SearchResultHttpTask继承自AsyncTask类，从而实现在后台线程中发送网络请求*/
    private class SearchResultHttpTask extends AsyncTask<BookType,Void,Void>
            implements AdapterView.OnItemClickListener {

        private static final String INNER_TAG = "SearchResultHttpTask";

        @Override
        protected Void doInBackground(BookType... bookTypes) {
            BookType bookType = bookTypes[0];
            Log.d(TAG, "get BookType: " + bookType.getTypeName());
            HttpURLConnection connection = null;
            try {
                String address = Uri.parse("http://route.showapi.com/211-2").buildUpon()
                        .appendQueryParameter("showapi_appid", "19226")
                        .appendQueryParameter("keyword", "")
                        .appendQueryParameter("typeId", bookType.getTypeId())
                        .appendQueryParameter("pre_match", "")
                        .appendQueryParameter("page", "")
                        .appendQueryParameter("showapi_sign", "53d7529210944090a664e3bb9e8a32de")
                        .build().toString();
                URL url = new URL(address);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(1000);
                connection.setReadTimeout(1000);
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                //解析并保存到数据库
                JsonParserUtil.parseBook(vrDatabase, response.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            bookList = vrDatabase.loadBooks();
            if (bookList != null) {
                adapter = new BookAdapter(bookList);
                lvBook.setAdapter(adapter);
                lvBook.setOnItemClickListener(this);
            } else {
                Toast.makeText(getActivity(), "连接失败",Toast.LENGTH_LONG).show();
            }
        }

        //响应ListView子项点击事件。即点击查询结果列表中的书时的操作
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }


    /*使用内部类自定义适配器，继承自ArrayAdapter。*/
    private class BookAdapter extends ArrayAdapter<Book> {

        public BookAdapter(List<Book> bookList) {
            super(getActivity(), 0, bookList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(
                        R.layout.list_item_book, parent, false);
            }
            Book book = getItem(position);
            TextView tvBookName = (TextView) convertView.findViewById(
                    R.id.tv_searchresult_bookname);
            tvBookName.setText(book.getBookName());

            TextView tvAuthor = (TextView) convertView.findViewById(
                    R.id.tv_searchresult_author);
            tvAuthor.setText(book.getAuthor());

            TextView tvNewChapter = (TextView) convertView.findViewById(
                    R.id.tv_searchresult_newchapter);
            tvNewChapter.setText(book.getNewChapter());

            return convertView;
        }
    }


}












