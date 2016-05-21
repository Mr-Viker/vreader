package com.viker.android.vreader.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.viker.android.vreader.R;
import com.viker.android.vreader.activity.BookCityActivity;

/**
 * Created by Viker on 2016/5/21.
 * VReader的主页面，UI主要包括标题栏title和书架bookcase。其托管于BooRackActivity。
 */
public class BookRackFragment extends Fragment implements View.OnClickListener {

    private static final int REQUEST_CODE=0; //定义启动BookCityActivity的请求码
    private Context context; //定义BookRackFragment的上下文

    private Button btnSetting;
    private Button btnBookCity;
    private GridView gvBookcase;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();//获取托管Activity为上下文
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookrack, container, false);

        btnSetting = (Button) view.findViewById(R.id.btn_bookrack_setting);
        btnBookCity = (Button) view.findViewById(R.id.btn_bookrack_bookcity);
        gvBookcase = (GridView) view.findViewById(R.id.gv_bookrack_bookcase);
        btnSetting.setOnClickListener(this);
        btnBookCity.setOnClickListener(this);
        return view;
    }

    //响应各组件的点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bookrack_setting:
                Toast.makeText(getActivity(), "you clicked setting.", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_bookrack_bookcity:
                 /*当点击btnBookCity后，会启动联网搜索的BookCityActivity，当用户Back时会通过
                  *setResult方法回传一个标志，根据该标志可知道用户是否完成将书籍存入数据库的信息，
                  * 所以会在回调方法onActivityResult（）中作出相应的操作。[如在bookcase上新增所
                  * 存入的书等]*/
                Intent toBookCityIntent = new Intent(context, BookCityActivity.class);
                startActivityForResult(toBookCityIntent, REQUEST_CODE);
                break;
            default:
                break;
        }
    }


}









