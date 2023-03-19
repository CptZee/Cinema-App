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

public class ScheduleHelper extends SQLiteOpenHelper {

    private Context context;
    private String table;

    public ScheduleHelper(Context context) {
        super(context, "CinemaDB", null, 1);
        this.table = "Schedules";
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try{
            db.execSQL("CREATE TABLE " + table + "(" +
                    "id int PRIMARY KEY AUTOINCREMENT," +
                    "movieID int NOT NULL," +
                    "time int NOT NULL," +
                    "date int NOT NULL");
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

    public void insert(Schedule data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = prepareData(data);
        db.insert(table, null, content);
    }

    public void delete(Schedule data){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] where = {Integer.toString(data.getId())};
        db.delete(table, data.getId() + " = ?", where);
    }

    public void update(Schedule data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = prepareData(data);
        String[] where = {Integer.toString(data.getId())};
        db.update(table, content, data.getId() + " = ?", where);
    }

    public Schedule get(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor dataReader = db.rawQuery("SELECT * FROM " + table + " WHERE id = " + id,
                null);
        Schedule data = prepareData(dataReader);
        return data;
    }

    public List<Schedule> get(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor dataReader = db.rawQuery("SELECT * FROM " + table,null);
        List<Schedule> list = new ArrayList<>();
        while(dataReader.moveToNext()){
            Schedule data = prepareData(dataReader);
            list.add(data);
        }
        return list;
    }

    private ContentValues prepareData(Schedule data){
        ContentValues content = new ContentValues();
        content.put("movieID", data.getMovieID());
        content.put("time", data.getTime().getTime());
        content.put("date",data.getDate().getTime());
        return content;
    }

    private Schedule prepareData(Cursor dataReader){
        Schedule data = new Schedule();
        data.setId(dataReader.getInt(0));
        data.setMovieID(dataReader.getInt(1));
        data.setTime(new Time(dataReader.getLong(2)));
        data.setDate(new Date(dataReader.getLong(3)));

        return data;
    }
}
