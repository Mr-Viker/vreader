package com.viker.android.vreader.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.viker.android.vreader.modle.Book;
import com.viker.android.vreader.modle.Chapter;
import com.viker.android.vreader.modle.ChapterContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Viker on 2016/5/20.
 * 封装一些有关数据库的操作方法进VRDatabase类中，方便操作。如数据库的CRUD。
 * 注意：VRDatabase不是数据库！只是数据库的操作的集合类。
 */
public class VRDatabase {

    private static final String TAG = "VRDatabase";

    private static final String DB_NAME = "v_reader"; //数据库名
    private static final int VERSION = 1; //数据库版本号

    private static VRDatabase vrDatabase;
    private SQLiteDatabase db;

    //利用单例模式创建VRDatabase实例，可以减少堆内存的浪费
    private VRDatabase(Context context) {
        VRSQLiteOpenHelper helper = new VRSQLiteOpenHelper(context, DB_NAME, null, VERSION);
        db = helper.getWritableDatabase();
    }

    public synchronized static VRDatabase getVrDatabase(Context context) {
        if (vrDatabase == null) {
            vrDatabase = new VRDatabase(context);
        }
        return vrDatabase;
    }

/*    //将BookType实例存储到数据库
    public void saveBookType(BookType bookType) {
        if (bookType != null) {
            ContentValues values = new ContentValues();
            values.put("type_id", bookType.getTypeId());
            values.put("type_name", bookType.getTypeName());
            db.insert("BookType", null, values);
            Log.d(TAG + " saveBookType", values.toString());
        }
    }

    //从数据库读取所有的BookType
    public List<BookType> loadBookTypes() {
        List<BookType> bookTypes = new ArrayList<>();
        Cursor cursor = db.query("BookType", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                BookType bookType = new BookType();
                bookType.setId(cursor.getInt(cursor.getColumnIndex("id")));
                bookType.setTypeId(cursor.getString(cursor.getColumnIndex("type_id")));
                bookType.setTypeName(cursor.getString(cursor.getColumnIndex("type_name")));
                Log.d(TAG + " loadBookType", bookType.getId() + bookType.getTypeId() +
                        bookType.getTypeName());
                bookTypes.add(bookType);
            } while (cursor.moveToNext());
        }
        return bookTypes;
    }*/

    //将Book实例存储到数据库
    public void saveBook(Book book) {
        if (book != null) {
            ContentValues values = new ContentValues();
            values.put("book_id", book.getBookId());
            values.put("author", book.getAuthor());
            values.put("book_name", book.getBookName());
            values.put("new_chapter", book.getNewChapter());
            values.put("size", book.getSize());
            values.put("type_id", book.getTypeId());
            values.put("type_name", book.getTypeName());
            values.put("update_time", book.getUpdateTime());
            db.insert("Book", null, values);
        }
    }

    //从数据库中读取某分类下所有的Book
    public List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        Cursor cursor = db.query("Book", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Book book = new Book();
                book.setId(cursor.getInt(cursor.getColumnIndex("id")));
                book.setBookId(cursor.getString(cursor.getColumnIndex("book_id")));
                book.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
                book.setBookName(cursor.getString(cursor.getColumnIndex("book_name")));
                book.setNewChapter(cursor.getString(cursor.getColumnIndex("new_chapter")));
                book.setSize(cursor.getString(cursor.getColumnIndex("size")));
                book.setTypeId(cursor.getString(cursor.getColumnIndex("type_id")));
                book.setTypeName(cursor.getString(cursor.getColumnIndex("type_name")));
                book.setUpdateTime(cursor.getString(cursor.getColumnIndex("update_time")));
                books.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return books;
    }

    //将Chapter实例存储到数据库中
    public void saveChapter(Chapter chapter) {
        if (chapter != null) {
            ContentValues values = new ContentValues();
            values.put("chapter_id", chapter.getChapterId());
            values.put("chapter_name", chapter.getChapterName());
            values.put("book_id", chapter.getBookId());
            values.put("book_name", chapter.getBookName());
            db.insert("Chapter", null, values);
        }
    }

    //从数据库读取某书中所有的Chapter
    public List<Chapter> loadChapters() {
        List<Chapter> chapterList = new ArrayList<>();
        Cursor cursor = db.query("Chapter", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Chapter chapter = new Chapter();
                chapter.setId(cursor.getInt(cursor.getColumnIndex("id")));
                chapter.setChapterId(cursor.getString(cursor.getColumnIndex("chapter_id")));
                chapter.setChapterName(cursor.getString(cursor.getColumnIndex("chapter_name")));
                chapter.setBookId(cursor.getString(cursor.getColumnIndex("book_id")));
                chapter.setBookName(cursor.getString(cursor.getColumnIndex("book_name")));
                chapterList.add(chapter);
            } while (cursor.moveToNext());
        }
        return chapterList;
    }

    //将ChapterContent实例存储到数据库中
    public void saveChapterContent(ChapterContent chapterContent) {
        if (chapterContent != null) {
            ContentValues values = new ContentValues();
            values.put("chapter_id", chapterContent.getChapterId());
            values.put("chapter_name", chapterContent.getChapterName());
            values.put("content", chapterContent.getContent());
            values.put("book_id", chapterContent.getBookId());
            db.insert("ChapterContent", null, values);
        }
    }

    //从数据库中读取某章节中的所有Content
    public List<ChapterContent> loadChapterContents() {
        List<ChapterContent> chapterContentList = new ArrayList<>();
        Cursor cursor = db.query("ChapterContent", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                ChapterContent chapterContent = new ChapterContent();
                chapterContent.setId(cursor.getInt(cursor.getColumnIndex("id")));
                chapterContent.setChapterId(cursor.getString(cursor.getColumnIndex("chapter_id")));
                chapterContent.setChapterName(cursor.getString(
                        cursor.getColumnIndex("chapter_name")));
                chapterContent.setContent(cursor.getString(
                        cursor.getColumnIndex("content")));
                chapterContent.setBookId(cursor.getString(
                        cursor.getColumnIndex("book_id")));
                chapterContentList.add(chapterContent);
            } while (cursor.moveToNext());
        }
        return chapterContentList;
    }





}









