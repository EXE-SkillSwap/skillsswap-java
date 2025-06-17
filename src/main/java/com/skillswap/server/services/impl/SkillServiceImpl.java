package com.skillswap.server.services.impl;

import com.skillswap.server.entities.Skills;
import com.skillswap.server.repositories.SkillsRepository;
import com.skillswap.server.services.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    private final SkillsRepository skillsRepository;

    @Override
    public List<Skills> getAllSkills() {
        return skillsRepository.findAll();
    }
}
