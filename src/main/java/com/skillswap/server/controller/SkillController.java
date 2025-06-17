package com.skillswap.server.controller;

import com.skillswap.server.entities.Skills;
import com.skillswap.server.services.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/skills")
@RestController
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @GetMapping
    public ResponseEntity<List<Skills>> getAllSkills() {
        List<Skills> skills = skillService.getAllSkills();
        return ResponseEntity.ok(skills);
    }
}
