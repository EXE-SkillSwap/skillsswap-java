package com.skillswap.server.repositories;

import com.skillswap.server.entities.User;
import com.skillswap.server.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    List<User> findByRole(Role role);
    Page<User> findAllByRoleAndIdNot(Role role, int currentUserId, Pageable pageable);
}
