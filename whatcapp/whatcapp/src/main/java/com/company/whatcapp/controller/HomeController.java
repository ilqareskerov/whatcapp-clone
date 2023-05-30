package com.company.whatcapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequestMapping
@RestController
public class HomeController {
     @RequestMapping("/")
     public ResponseEntity<String> homeController(){
        return ResponseEntity.ok("Hello World");
    }
}
