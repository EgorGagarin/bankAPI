package com.gagarin.bankAPI.repository;

import com.gagarin.bankAPI.entity.ERole;
import com.gagarin.bankAPI.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
