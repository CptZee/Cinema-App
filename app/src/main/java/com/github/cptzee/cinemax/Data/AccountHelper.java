package com.github.cptzee.cinemax.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class AccountHelper extends SQLiteOpenHelper {

    private Context context;
    private String table;

    public AccountHelper(Context context) {
        super(context, "CinemaDB", null, 1);
        this.table = "Accounts";
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try{
          db.execSQL("CREATE TABLE " + table + "(" +
                  "id int PRIMARY KEY AUTOINCREMENT," +
                  "firstName varchar(30) NOT NULL," +
                  "familyName varchar(30) NOT NULL," +
                  "email varchar(30) NOT NULL," +
                  "contactNo varchar(30) NOT NULL");
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

    public void insert(Account data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = prepareData(data);
        db.insert(table, null, content);
    }

    public void delete(Account data){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] where = {Integer.toString(data.getId())};
        db.delete(table, data.getId() + " = ?", where);
    }

    public void update(Account data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = prepareData(data);
        String[] where = {Integer.toString(data.getId())};
        db.update(table, content, data.getId() + " = ?", where);
    }

    public Account get(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor dataReader = db.rawQuery("SELECT * FROM " + table + " WHERE id = " + id,
                null);
        Account data = prepareData(dataReader);
        return data;
    }

    public List<Account> get(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor dataReader = db.rawQuery("SELECT * FROM " + table,null);
        List<Account> list = new ArrayList<>();
        while(dataReader.moveToNext()){
            Account data = prepareData(dataReader);
            list.add(data);
        }
        return list;
    }

    private ContentValues prepareData(Account data){
        ContentValues content = new ContentValues();
        content.put("firstName", data.getFirstName());
        content.put("familyName", data.getFamilyName());
        content.put("email", data.getEmail());
        content.put("contact", data.getContactNo());
        return content;
    }

    private Account prepareData(Cursor dataReader){
        Account data = new Account();
        data.setId(dataReader.getInt(0));
        data.setFirstName(dataReader.getString(1));
        data.setFamilyName(dataReader.getString(2));
        data.setEmail(dataReader.getString(3));
        data.setContactNo(dataReader.getString(4));

        return data;
    }
}
