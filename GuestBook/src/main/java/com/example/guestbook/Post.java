package com.example.guestbook;

import java.time.LocalDate;

public class Post {
    private String body;
    private LocalDate date;
    private int author_id;

    public Post(String body, LocalDate date, int author_id) {
        this.body = body;
        this.date = date;
        this.author_id = author_id;
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
                "body='" + body + '\'' +
                ", date=" + date +
                ", author_id=" + author_id +
                '}';
    }
}
