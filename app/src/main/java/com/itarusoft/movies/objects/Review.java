package com.itarusoft.movies.objects;

public class Review {
    private String author;

    private String content;

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public Review (String author, String content){
        setAuthor(author);
        setContent(content);
    }
}
