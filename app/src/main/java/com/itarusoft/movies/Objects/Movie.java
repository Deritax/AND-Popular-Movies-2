package com.itarusoft.movies.Objects;

public class Movie {

    private String title;

    private String release;

    private String poster;

    private String vote;

    private String synopsis;

    private String id;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRelease(String release){
        this.release = release;
    }

    public void setPoster(String poster){
        this.poster = poster;
    }

    public void setVote(String vote){
        this.vote = vote;
    }

    public void setSynopsis(String synopsis){
        this.synopsis = synopsis;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public String getRelease(){
        return release;
    }

    public String getPoster(){
        return poster;
    }

    public String getVote(){
        return vote;
    }

    public String getSynopsis(){
        return synopsis;
    }

    public String getId(){
        return id;
    }

    public Movie(String title, String release, String poster, String vote, String synopsis, String id){
        setTitle(title);
        setRelease(release);
        setPoster(poster);
        setVote(vote);
        setSynopsis(synopsis);
        setId(id);
    }
}
