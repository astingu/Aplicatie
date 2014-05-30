package com.springapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by astingu on 5/26/2014.
 */
public class PoolMessageNotReady {

    @JsonProperty("message")
    private String message;

    private String detail;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
