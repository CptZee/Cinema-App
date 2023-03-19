package com.github.cptzee.cinemax.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MovieHelper extends SQLiteOpenHelper {

    private Context context;
    private String table;

    public MovieHelper(Context context) {
        super(context, "CinemaDB", null, 1);
        this.table = "Movies";
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try{
            db.execSQL("CREATE TABLE " + table + "(" +
                    "id int PRIMARY KEY AUTOINCREMENT," +
                    "title varchar(30) NOT NULL," +
                    "categoryID int NOT NULL," +
                    "cinemaID int NOT NULL," +
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

    public void insert(Movie data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = prepareData(data);
        db.insert(table, null, content);
    }

    public void delete(Movie data){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] where = {Integer.toString(data.getId())};
        db.delete(table, data.getId() + " = ?", where);
    }

    public void update(Movie data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = prepareData(data);
        String[] where = {Integer.toString(data.getId())};
        db.update(table, content, data.getId() + " = ?", where);
    }

    public Movie get(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor dataReader = db.rawQuery("SELECT * FROM " + table + " WHERE id = " + id,
                null);
        Movie data = prepareData(dataReader);
        return data;
    }

    public List<Movie> get(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor dataReader = db.rawQuery("SELECT * FROM " + table,null);
        List<Movie> list = new ArrayList<>();
        while(dataReader.moveToNext()){
            Movie data = prepareData(dataReader);
            list.add(data);
        }
        return list;
    }

    private ContentValues prepareData(Movie data){
        ContentValues content = new ContentValues();
        content.put("title", data.getTitle());
        content.put("categoryID", data.getCategoryID());
        content.put("cinemaID", data.getCinemaID());
        content.put("scheduleID", data.getScheduleID());
        return content;
    }

    private Movie prepareData(Cursor dataReader){
        Movie data = new Movie();
        data.setId(dataReader.getInt(0));
        data.setTitle(dataReader.getString(1));
        data.setCategoryID(dataReader.getInt(2));
        data.setCinemaID(dataReader.getInt(3));
        data.setScheduleID(dataReader.getInt(4));

        return data;
    }
}
