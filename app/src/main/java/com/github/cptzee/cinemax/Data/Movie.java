package com.github.cptzee.cinemax.Data;

public class Movie {
    private int id;
    private String title;
    private int CategoryID;
    private int CinemaID;
    private int ScheduleID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }

    public int getCinemaID() {
        return CinemaID;
    }

    public void setCinemaID(int cinemaID) {
        CinemaID = cinemaID;
    }

    public int getScheduleID() {
        return ScheduleID;
    }

    public void setScheduleID(int scheduleID) {
        ScheduleID = scheduleID;
    }
}
