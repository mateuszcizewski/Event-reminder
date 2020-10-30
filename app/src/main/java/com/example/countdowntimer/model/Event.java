package com.example.countdowntimer.model;

import java.util.Date;

public class Event {

    String name;
    Date date;
    Date alarm;

    public Event(String name, Date date) {
        this.name = name;
        this.date = date;
    }

    public Event(String name, Date date, Date alarm) {
        this.name = name;
        this.date = date;
        this.alarm = alarm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getAlarm() {
        return alarm;
    }

    public void setAlarm(Date alarm) {
        this.alarm = alarm;
    }
}
