package com.skillswap.server.repositories;

import com.skillswap.server.entities.ProfileImages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileImageRepository extends JpaRepository<ProfileImages, Integer> {

    List<ProfileImages> findByUserId(int userId);
}
