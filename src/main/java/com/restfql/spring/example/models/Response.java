package com.restfql.spring.example.models;

import com.restfql.spring.example.models.Tags;

public class Response {
    public Response(String message, String description, Tags tags){
        this.message = message;
        this.description = description;
        this.tags = tags;
    }
    public String message;
    public String description;
    public Tags tags;
}
