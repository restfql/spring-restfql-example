package com.restfql.spring.example;

public class Response {
    Response(String message, String description){
        this.message = message;
        this.description = description;
    }
    public String message;
    public String description;
}
