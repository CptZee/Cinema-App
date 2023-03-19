package com.github.cptzee.cinemax.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class CredentialHelper extends SQLiteOpenHelper {

    private Context context;
    private String table;

    public CredentialHelper(Context context) {
        super(context, "CinemaDB", null, 1);
        this.table = "Credentials";
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try{
            db.execSQL("CREATE TABLE " + table + "(" +
                    "id int PRIMARY KEY AUTOINCREMENT," +
                    "username varchar(30) NOT NULL," +
                    "password varchar(30) NOT NULL");
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

    public void insert(Credential data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = prepareData(data);
        db.insert(table, null, content);
    }

    public void delete(Credential data){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] where = {Integer.toString(data.getId())};
        db.delete(table, data.getId() + " = ?", where);
    }

    public void update(Credential data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = prepareData(data);
        String[] where = {Integer.toString(data.getId())};
        db.update(table, content, data.getId() + " = ?", where);
    }

    public Credential get(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor dataReader = db.rawQuery("SELECT * FROM " + table + " WHERE id = " + id,
                null);
        Credential data = prepareData(dataReader);
        return data;
    }

    public List<Credential> get(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor dataReader = db.rawQuery("SELECT * FROM " + table,null);
        List<Credential> list = new ArrayList<>();
        while(dataReader.moveToNext()){
            Credential data = prepareData(dataReader);
            list.add(data);
        }
        return list;
    }

    private ContentValues prepareData(Credential data){
        ContentValues content = new ContentValues();
        content.put("username", data.getUsername());
        content.put("password", data.getPassword());
        return content;
    }

    private Credential prepareData(Cursor dataReader){
        Credential data = new Credential();
        data.setId(dataReader.getInt(0));
        data.setUsername(dataReader.getString(1));
        data.setPassword(dataReader.getString(2));

        return data;
    }
}
