package com.springTraining.jwtAuthentication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home")
public class AuthorizationController {

    @GetMapping
    public ResponseEntity<String> welcome()
    {
        return ResponseEntity.ok("Code worked");
    }
}
