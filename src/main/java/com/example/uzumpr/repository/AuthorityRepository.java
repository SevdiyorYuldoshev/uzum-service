package com.example.uzumpr.repository;

import com.example.uzumpr.model.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authorities, Integer> {
    List<Authorities> findAllByUsername(String username);
    boolean existsByUsernameAndAuthority(String username, String authority);
}
