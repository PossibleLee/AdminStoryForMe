package com.eos.adminstoryforme;

public class WritingData {
    public int author_id;
    public int comment_num;
    public String date;
    public int episode_num;
    public int hits;
    public int id;
    public String image;
    public boolean is_uploaded;
    public int series_id;
    public String series_name;
    public String title;

    public WritingData(int author_id, int comment_num, String date, int episode_num, int hits, int id, String image, boolean is_uploaded, int series_id, String series_name, String title) {
        this.author_id = author_id;
        this.comment_num = comment_num;
        this.date = date;
        this.episode_num = episode_num;
        this.hits = hits;
        this.id = id;
        this.image = image;
        this.is_uploaded = is_uploaded;
        this.series_id = series_id;
        this.series_name = series_name;
        this.title = title;
    }
}
