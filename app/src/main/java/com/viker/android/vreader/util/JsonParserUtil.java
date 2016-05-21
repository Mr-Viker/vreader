package com.viker.android.vreader.util;

import com.viker.android.vreader.database.VRDatabase;
import com.viker.android.vreader.modle.Book;
import com.viker.android.vreader.modle.BookType;
import com.viker.android.vreader.modle.Chapter;
import com.viker.android.vreader.modle.ChapterContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by Viker on 2016/5/20.
 * this class should be used to parse and handle JSON data.
 */
public class JsonParserUtil {

    //解析服务器返回的BookType数据并通过VRDatabase实例将之保存在本地数据库
    public static void parseBookType(VRDatabase vrDatabase, String response) {
        try {
            JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
            int flag = object.getInt("showapi_res_code");
            if ( flag != 0) {
                String errorInfo = object.getString("showapi_res_error");
                return;
            }
            //获取数据主体，包含一些所需信息的消息体
            JSONArray dataBody = object.getJSONArray("showapi_res_body");
            //从数据主体中获取所需的信息（全部书籍类目）
            JSONArray bookTypes = dataBody.getJSONArray(1);
            for (int i=0;i<bookTypes.length();i++) {
                JSONObject subObject = bookTypes.getJSONObject(i);
                BookType bookType = new BookType();
                bookType.setTypeId(subObject.getString("id"));
                bookType.setTypeName(subObject.getString("name"));
                vrDatabase.saveBookType(bookType);//保存到数据库
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //解析服务器返回的Book数据并通过VRDatabase实例将之保存在本地数据库
    public static void parseBook(VRDatabase vrDatabase, String response) {
        try {
            JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
            int flag = object.getInt("showapi_res_code");//返回标志，0为成功
            if ( flag != 0) {
                String errorInfo = object.getString("showapi_res_error");
                return;
            }
            JSONArray dataBody = object.getJSONArray("showapi_res_body");
            //获取服务器返回数据中数据主体中的查询分页，包括了总书籍数，总分页数，书籍列表等。因此需先获取该数据。
            JSONArray pageBean = dataBody.getJSONArray(0);
            //通过查询分页pageBean获取位于索引为2的书籍列表
            JSONArray books = pageBean.getJSONArray(2);
            for (int i = 0; i < books.length(); i++) {
                JSONObject subObject = books.getJSONObject(i);
                Book book = new Book();
                book.setBookId(subObject.getString("id"));
                book.setAuthor(subObject.getString("author"));
                book.setBookName(subObject.getString("name"));
                book.setNewChapter(subObject.getString("newChapter"));
                book.setSize(subObject.getString("size"));
                book.setTypeId(subObject.getString("type"));
                book.setTypeName(subObject.getString("typeName"));
                book.setUpdateTime(subObject.getString("updateTime"));
                vrDatabase.saveBook(book);//保存到数据库
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //解析服务器返回的Chapter数据并将之保存到数据库
    public static void parseChapter(VRDatabase vrDatabase, String response) {
        try {
            JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
            int flag = object.getInt("showapi_res_code");//返回标志，0为成功
            if (flag != 0) {
                String errorInfo = object.getString("showapi_res_error");
                return;
            }
            JSONArray dataBody = object.getJSONArray("showapi_res_body");
            //先获取数据主体中包括章节和书名等信息的“book”数据
            JSONArray aBookInfo = dataBody.getJSONArray(0);
            //通过aBookInfo数组获取其包括的书名，书籍id以及章节列表信息
            JSONArray chapters = aBookInfo.getJSONArray(1);

            //JSObject是包含key和value的键值对形式,因此不能直接toString转化
            JSONObject bookI = aBookInfo.getJSONObject(2);
            //获取key“id”所对应的value值
            String bookId = bookI.getString("id");
            //同上
            JSONObject bookN = aBookInfo.getJSONObject(3);
            String bookName = bookN.getString("name");
            for (int i=0;i<chapters.length();i++) {
                JSONObject subObject = chapters.getJSONObject(i);
                Chapter chapter = new Chapter();
                chapter.setBookId(bookId);
                chapter.setBookName(bookName);
                chapter.setChapterId(subObject.getString("cid"));
                chapter.setChapterName(subObject.getString("name"));
                vrDatabase.saveChapter(chapter);//保存到数据库
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //解析服务器返回的ChapterContent数据并将之保存到数据库
    public static void parseChapterContent(VRDatabase vrDatabase, String response) {
        try {
            JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
            int flag = object.getInt("showapi_res_code");//返回标志，0为成功
            if (flag != 0) {
                String errorInfo = object.getString("showapi_res_error");
                return;
            }
            JSONArray dataBody = object.getJSONArray("showapi_res_body");
            JSONObject chapterI = dataBody.getJSONObject(0);
            JSONObject chapterN = dataBody.getJSONObject(1);
            JSONObject bookI = dataBody.getJSONObject(2);
            JSONObject chapterC = dataBody.getJSONObject(4);
            //因为返回的是一个章节对应的内容，所以无需for循环遍历
            ChapterContent chapterContent = new ChapterContent();
            chapterContent.setChapterId(chapterI.getString("cid"));
            chapterContent.setChapterName(chapterN.getString("cname"));
            chapterContent.setBookId(bookI.getString("id"));
            chapterContent.setContent(chapterC.getString("txt"));
            vrDatabase.saveChapterContent(chapterContent);//保存到数据库
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}












