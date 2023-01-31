package com.android.mylogin;


public class model
{
    String alarm_time,task,time,date, title;



   model()
    {

    }
    public model(String alarm_time, String task, String  time, String date, String title) {
        this.alarm_time = alarm_time;
        this.task= task;
        this.time= time;
        this.date = date;
        this.title= title;



    }

    public String getalarm_time() {
        return  alarm_time;
    }

    public void setalarm_time(String  alarm_time) {
        this.alarm_time = alarm_time;
    }
    public String gettask() {
        return  task;
    }

    public void settask(String  task) {
        this.task= task;
    }

    public String gettime() {
        return  time;
    }

    public void settime(String  time) {
        this.time= time;
    }

    public String getdate() {
        return  date;
    }

    public void sedate(String  date) {
        this.date= date;
    }

    public String gettitle() {
        return  title;
    }

    public void setitle(String  title) {
        this.title= title;
    }




}
