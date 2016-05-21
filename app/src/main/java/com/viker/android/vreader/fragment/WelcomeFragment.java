package com.viker.android.vreader.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viker.android.vreader.R;
import com.viker.android.vreader.activity.BookRackActivity;

/**
 * Created by Viker on 2016/5/21.
 * 进入app时最先展示的界面，其托管于WelcomeActivity。
 * 该碎片主要用于展示欢迎图片，以及初始化一些数据。
 */
public class WelcomeFragment extends Fragment {

    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        return view;
    }

    //当托管Activity告诉Fragment其Activity.onCreate（）方法完成后，Fragment通过
    // Handler.postDelayed()向本线程发送一个将经过给定时间段后启动BookRackActivity的Runnable的操作。
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*创建本线程即UI线程的Handler[注：一个线程只有一个Handler，Handler与创建它时所在的线程绑定]
        * Handler中的postDelayed(Runnable r, long delayMillis)的意思是Causes the Runnable r
        * to be added to the message queue, to be run after the specified amount of
        * time elaps[即创建一个Runnable并发送到本线程的消息队列中，在经过给定的时间后执行
        * 该Runnable]*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent toBookRackIntent = new Intent(context, BookRackActivity.class);
                getActivity().startActivity(toBookRackIntent);
                getActivity().finish();
            }
        }, 2000);
    }

}
