package com.restfql.spring.example;

import com.restfql.spring.example.models.Response;
import com.restfql.spring.example.models.Tags;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {

    @GetMapping("/")
    public Response index() {
        return new Response("awesome message", "awesome description", new Tags(List.of("tag1, tag2"), List.of("tag1, tag2")));
    }

}
