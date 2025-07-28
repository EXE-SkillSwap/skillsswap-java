package com.skillswap.server.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
@RequiredArgsConstructor
public class TestController {

    @GetMapping
    public String test() {
        return "Test successful! Fix loi 10";
    }
}
