package com.mrf.mrfmaharashtra.Model;

public class NewsLetter {
    private  String News_id;
    private  String News_name;
    private  String News_description;
    private  String News_image;


    public NewsLetter(String news_id, String news_name, String news_description,String news_image) {
        this.News_id = news_id;
        this.News_name = news_name;
        this.News_description = news_description;
        this.News_image=news_image;
    }

    public NewsLetter() {

    }

    public String getNews_id() {
        return News_id;
    }

    public void setNews_id(String news_id) {
        News_id = news_id;
    }

    public String getNews_name() {
        return News_name;
    }

    public void setNews_name(String news_name) {
        News_name = news_name;
    }

    public String getNews_description() {
        return News_description;
    }

    public void setNews_description(String news_description) {
        News_description = news_description;
    }

    public String getNews_image() {
        return News_image;
    }

    public void setNews_image(String news_image) {
        News_image = news_image;
    }
}
