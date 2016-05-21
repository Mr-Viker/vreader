package com.viker.android.vreader.util;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Viker on 2016/5/20.
 * 继承AsyncTask，将需要进行的耗时操作如网络请求放在doInBackground()方法中
 */
public class HttpTask extends AsyncTask<String,void,void>{

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //开启新线程，进行网络请求
    protected Object doInBackground(String address) {
        HttpURLConnection connection = null;
        try {
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
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }



}
