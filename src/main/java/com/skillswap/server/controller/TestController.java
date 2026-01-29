package com.skillswap.server.controller;


import com.skillswap.server.services.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping
    public String test() {
        return "Test successful! Fix loi 11";
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getCacheUserById(@PathVariable int id) {
        return ResponseEntity.ok(testService.getUserById(id));
    }
}
