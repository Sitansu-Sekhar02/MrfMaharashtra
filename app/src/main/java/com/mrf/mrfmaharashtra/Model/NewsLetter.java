package com.mrf.mrfmaharashtra.Model;

public class NewsLetter {
    private  String News_id;
    private  String News_name;
    private  String News_description;
    private  String News_image;
    private  String notification_img;
    private  String notification_heading;
    private  String notification_description;
    private  String notification_date;







    public NewsLetter(String news_id, String news_name, String news_description,String news_image,String notification_img,String notification_heading,String notification_description,String notification_date ) {
        this.News_id = news_id;
        this.News_name = news_name;
        this.News_description = news_description;
        this.News_image=news_image;
        this.notification_img=notification_img;
        this.notification_heading=notification_heading;
        this.notification_description=notification_description;
        this.notification_date=notification_date;


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

    public String getNotification_img() {
        return notification_img;
    }

    public void setNotification_img(String notification_img) {
        this.notification_img = notification_img;
    }

    public String getNotification_heading() {
        return notification_heading;
    }

    public void setNotification_heading(String notification_heading) {
        this.notification_heading = notification_heading;
    }

    public String getNotification_description() {
        return notification_description;
    }

    public void setNotification_description(String notification_description) {
        this.notification_description = notification_description;
    }

    public String getNotification_date() {
        return notification_date;
    }

    public void setNotification_date(String notification_date) {
        this.notification_date = notification_date;
    }
}
