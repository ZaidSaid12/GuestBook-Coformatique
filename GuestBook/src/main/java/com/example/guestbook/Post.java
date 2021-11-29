package com.example.guestbook;

import java.time.LocalDate;

public class Post {
    private int post_id;
    private String body;
    private LocalDate date;
    private int author_id;

    public Post(int id, String body, LocalDate date, int author_id) {
        this.post_id = id;
        this.body = body;
        this.date = date;
        this.author_id = author_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    @Override
    public String toString() {
        return "Post{" +
                "post_id=" + post_id +
                ", body='" + body + '\'' +
                ", date=" + date +
                ", author_id=" + author_id +
                '}';
    }
}
