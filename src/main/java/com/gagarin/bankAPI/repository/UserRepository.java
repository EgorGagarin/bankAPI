package com.gagarin.bankAPI.repository;

import com.gagarin.bankAPI.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
