package com.viker.android.vreader.fragment;

import android.app.Fragment;
import android.content.Intent;
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
import com.viker.android.vreader.activity.SearchResultActivity;
import com.viker.android.vreader.modle.BookType;
import com.viker.android.vreader.util.JsonParserUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Viker on 2016/5/21.
 */
public class BookCityFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "BookCityFragment";

    public List<BookType> bookTypeList;
    private BookTypeAdapter adapter; //自定义的适配器，用于显示书籍类目

    private Button btnBack; //返回键
    private Button btnSearch; //搜索键
    private ListView lvBookType;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bookTypeList = new ArrayList<>();

        //创建新线程发送网络请求。
        new BookCityHttpTask().execute();
    }


    //加载布局。
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookcity, container, false);

        btnBack = (Button) view.findViewById(R.id.btn_boocity_back);
        btnSearch = (Button) view.findViewById(R.id.btn_bookcity_search);
        lvBookType = (ListView) view.findViewById(R.id.lv_bookcity_booktype);
        //注册各组件的点击事件。
        btnBack.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

        return view;
    }


    //响应标题栏按钮的点击事件。
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_boocity_back: //返回键
                getActivity().finish();
                break;

            case R.id.btn_bookcity_search: //搜索键

                break;

            default:
                break;
        }
    }


    /*建立内部类BookCityHttpTask继承自AsyncTask类，从而实现在后台线程中发送网络请求*/
    private class BookCityHttpTask extends AsyncTask<Void, Void, List<BookType>>
            implements AdapterView.OnItemClickListener {

        private static final String INNER_TAG = "BookCityHttpTask";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //后台线程操作，进行网络请求
        protected List<BookType> doInBackground(Void... v) {
            HttpURLConnection connection = null;
            try {
                String address = "http://route.showapi.com/211-3?showapi_appid=19226&" +
                        "showapi_sign=53d7529210944090a664e3bb9e8a32de";
                URL url = new URL(address);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                //将从服务器上获取到的数据从JSON格式解析成所需信息格式并返回给BookType数组。
                bookTypeList = JsonParserUtil.parseBookType(response.toString());
                return bookTypeList;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return null;
        }

        //当doInBackground方法完成后调用此方法更新UI，如将获得的书籍类目显示在UI上。
        @Override
        protected void onPostExecute(List<BookType> bookTypeList) {
            if (bookTypeList != null) {
                adapter = new BookTypeAdapter(bookTypeList);
                lvBookType.setAdapter(adapter);
                lvBookType.setOnItemClickListener(this);
            } else {
                Toast.makeText(getActivity(), "连接失败", Toast.LENGTH_LONG).show();
            }
        }

        //ListView的点击事件操作,获得点击项实例，启动搜索结果页面，并将该实例作为参数传入。
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            BookType bookType = adapter.getItem(position);
            Log.d(INNER_TAG + " LV", "you click: " + bookType.getTypeName());
            Intent toSearchResultActivity = new Intent(getActivity(),
                    SearchResultActivity.class);
            toSearchResultActivity.putExtra(SearchResultFragment.EXTRA_BOOK_TYPE, bookType);
            startActivity(toSearchResultActivity);
        }
    }


    /*使用内部类自定义适配器，继承自ArrayAdapter。
    * 谨记：当getCount(）为0时[即数据源为空时]是不会调用getView()方法的。*/
    private class BookTypeAdapter extends ArrayAdapter<BookType> {

        private static final String INNER_TAG = "BookTypeAdapter";

        public BookTypeAdapter(List<BookType> bookTypeList) {
            super(getActivity(), 0, bookTypeList);
            Log.d(INNER_TAG, "Adapter创建成功");
            Log.d(INNER_TAG + " getCout", getCount() + "");
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().
                        inflate(R.layout.list_item_booktype, null);
                Log.d(INNER_TAG + " getView", "convertView加载完毕");
            }
            //获得滑入屏幕的子项位置所对应的对象
            BookType bookType = getItem(position);
            Log.d(INNER_TAG + " bookTpe", position + bookType.toString());
            //将BookType实例的信息show到UI。
            TextView tvBookTypeId = (TextView) convertView.findViewById(
                    R.id.tv_bookcity_booktype_id);
            tvBookTypeId.setText(bookType.getTypeId());
            TextView tvBookTypeName = (TextView) convertView.findViewById(
                    R.id.tv_bookcity_booktype_name);
            tvBookTypeName.setText(bookType.getTypeName());
            Log.d(INNER_TAG, "return 第" + position + "项");
            return convertView;
        }
    }


}
