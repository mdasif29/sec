package com.projectsecuirty.security.service;

import java.util.List;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projectsecuirty.security.entity.AppUser;
import com.projectsecuirty.security.repo.UserRepository;


@Service
public class UserService implements UserDetailsService {

    @Autowired private UserRepository repo;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        AppUser user = repo.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("User not found");
        // wrap in Spring’s User, granting authorities from your role field:
        return User.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .roles(user.getRole().replace("ROLE_",""))  // Spring adds "ROLE_" prefix
            .build();
    }

    /** Called by registration controller **/
    public void saveUser(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // ensure role starts with ROLE_
        if (!user.getRole().startsWith("ROLE_")) {
            user.setRole("ROLE_" + user.getRole());
        }

        repo.save(user);
    }

    public List<AppUser> getAllUsers() {
        return repo.findAll();
    }

    public void deleteUser(Long id) {
        repo.deleteById(id);
    }
}
