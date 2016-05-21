package com.viker.android.vreader.modle;

/**
 * Created by Viker on 2016/5/20.
 * 书籍类目model
 */
public class BookType {

    private int id;
    private String typeId;
    private String typeName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

}
