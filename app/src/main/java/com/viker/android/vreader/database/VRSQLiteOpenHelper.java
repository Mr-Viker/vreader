package com.viker.android.vreader.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Viker on 2016/5/20.
 * 创建数据库和表
 */
public class VRSQLiteOpenHelper extends SQLiteOpenHelper {

    //BookType表建表语句
    private static final String CREATE_BOOK_TYPE = "create table BookType (" +
            "id integer primary key autoincrement," +
            "type_id text," +
            "type_name text)";

    //Book表建表语句
    private static final String CREATE_BOOK = "create table Book (" +
            "id integer primary key autoincrement," +
            "book_id text," +
            "author text," +
            "book_name text," +
            "new_chapter text," +
            "size text," +
            "type_id text," +
            "type_name text," +
            "update_time text)";

    //Chapter表建表语句
    private static final String CREATE_CHAPTER = "create table Chapter (" +
            "id integer primary key autoincrement," +
            "chapter_id text," +
            "chapter_name text," +
            "book_id text," +
            "book_name text)";

    //ChapterContent表建表语句
    private static final String CREATE_CHAPTER_CONTENT = "create table ChapterContent (" +
            "id integer primary key autoincrement," +
            "chapter_id text," +
            "chapter_name text," +
            "content text," +
            "book_id text)";

    public VRSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory
            factory, int version) {
        super(context, name, factory, version);
    }

    //创建数据库和表
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK_TYPE);
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CHAPTER);
        db.execSQL(CREATE_CHAPTER_CONTENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
