package com.skillswap.server.repositories;

import com.skillswap.server.entities.Membership;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MembershipRepository extends JpaRepository<Membership, Integer> {

    List<Membership> findAllByIsDeletedFalse();

    Page<Membership> findAll(Specification<Membership> spec, Pageable pageable);
}
