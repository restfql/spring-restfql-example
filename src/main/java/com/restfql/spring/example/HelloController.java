package com.restfql.spring.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public Response index() {
        return new Response("awesome message", "awesome description");
    }

}
