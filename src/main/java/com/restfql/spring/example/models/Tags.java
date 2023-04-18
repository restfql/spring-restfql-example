package com.restfql.spring.example.models;

import java.util.List;

public class Tags {
    public List<String> mandatory;
    public List<String> optional;

    public Tags(List<String> mandatory, List<String> optional) {
        this.mandatory = mandatory;
        this.optional = optional;
    }

}
