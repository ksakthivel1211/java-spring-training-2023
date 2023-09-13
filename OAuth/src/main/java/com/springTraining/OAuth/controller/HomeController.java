package com.springTraining.OAuth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home()
    {
        return "Welcome home";
    }

    @GetMapping("/secured")
    public String secured()
    {
        return "secured content";
    }
}
