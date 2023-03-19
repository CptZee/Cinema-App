package com.github.cptzee.cinemax.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class TransactionHelper extends SQLiteOpenHelper {

    private Context context;
    private String table;

    public TransactionHelper(Context context) {
        super(context, "CinemaDB", null, 1);
        this.table = "Transactions";
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try{
            db.execSQL("CREATE TABLE " + table + "(" +
                    "id int PRIMARY KEY AUTOINCREMENT," +
                    "accountID int NOT NULL," +
                    "movieID int NOT NULL," +
                    "scheduleID int NOT NULL");
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

    public void insert(Transaction data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = prepareData(data);
        db.insert(table, null, content);
    }

    public void delete(Transaction data){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] where = {Integer.toString(data.getId())};
        db.delete(table, data.getId() + " = ?", where);
    }

    public void update(Transaction data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = prepareData(data);
        String[] where = {Integer.toString(data.getId())};
        db.update(table, content, data.getId() + " = ?", where);
    }

    public Transaction get(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor dataReader = db.rawQuery("SELECT * FROM " + table + " WHERE id = " + id,
                null);
        Transaction data = prepareData(dataReader);
        return data;
    }

    public List<Transaction> get(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor dataReader = db.rawQuery("SELECT * FROM " + table,null);
        List<Transaction> list = new ArrayList<>();
        while(dataReader.moveToNext()){
            Transaction data = prepareData(dataReader);
            list.add(data);
        }
        return list;
    }

    private ContentValues prepareData(Transaction data){
        ContentValues content = new ContentValues();
        content.put("accountID", data.getAccountID());
        content.put("movieID", data.getMovieID());
        return content;
    }

    private Transaction prepareData(Cursor dataReader){
        Transaction data = new Transaction();
        data.setId(dataReader.getInt(0));
        data.setAccountID(dataReader.getInt(1));
        data.setMovieID(dataReader.getInt(2));

        return data;
    }
}
