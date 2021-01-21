package com.eos.adminstoryforme;

public class Notice_data {
    public int id;
    public String contents;
    public String title;
    public String date;
    public int watcher;

    public Notice_data(int id, String title, String date, int watcher, String contents){
        this.id = id;
        this.title = title;
        this.date = date;
        this.watcher = watcher;
        this.contents = contents;
    }
}
