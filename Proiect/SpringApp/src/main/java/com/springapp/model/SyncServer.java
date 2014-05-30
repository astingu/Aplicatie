package com.springapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by astingu on 5/26/2014.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class SyncServer {

    @JsonProperty("noEntries")
    private int noEntries;

    @JsonProperty("content")
    private List<Content> content;

    public int getNoEntries() {
        return noEntries;
    }

    public void setNoEntries(int noEntries) {
        this.noEntries = noEntries;
    }

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }
}
