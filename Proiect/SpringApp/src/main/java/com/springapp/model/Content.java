package com.springapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by astingu on 5/26/2014.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Content {

    @JsonProperty("title")
    private String title;

    @JsonProperty("poster")
    private String poster;

    @JsonProperty("url")
    private String url;

    @JsonProperty("year")
    private int year;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }


}
