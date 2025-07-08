package com.projectsecuirty.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectsecuirty.security.entity.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);
}
