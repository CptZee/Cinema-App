package com.github.cptzee.cinemax.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class CinemaHelper extends SQLiteOpenHelper {

    private Context context;
    private String table;

    public CinemaHelper(Context context) {
        super(context, "CinemaDB", null, 1);
        this.table = "Cinemas";
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try{
            db.execSQL("CREATE TABLE " + table + "(" +
                    "id int PRIMARY KEY AUTOINCREMENT," +
                    "name varchar(30) NOT NULL");
        }catch (SQLException e){
            //Ignore as it is probably the table existing in the first place.
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            db.execSQL("DROP TABLE IF EXISTS " + table);
            onCreate(db);
        }catch (SQLException e){
            //Ignore for now
        }
    }

    public void insert(Cinema data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = prepareData(data);
        db.insert(table, null, content);
    }

    public void delete(Cinema data){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] where = {Integer.toString(data.getId())};
        db.delete(table, data.getId() + " = ?", where);
    }

    public void update(Cinema data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = prepareData(data);
        String[] where = {Integer.toString(data.getId())};
        db.update(table, content, data.getId() + " = ?", where);
    }

    public Cinema get(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor dataReader = db.rawQuery("SELECT * FROM " + table + " WHERE id = " + id,
                null);
        Cinema data = prepareData(dataReader);
        return data;
    }

    public List<Cinema> get(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor dataReader = db.rawQuery("SELECT * FROM " + table,null);
        List<Cinema> list = new ArrayList<>();
        while(dataReader.moveToNext()){
            Cinema data = prepareData(dataReader);
            list.add(data);
        }
        return list;
    }

    private ContentValues prepareData(Cinema data){
        ContentValues content = new ContentValues();
        content.put("name", data.getName());
        return content;
    }

    private Cinema prepareData(Cursor dataReader){
        Cinema data = new Cinema();
        data.setId(dataReader.getInt(0));
        data.setName(dataReader.getString(1));

        return data;
    }
}
