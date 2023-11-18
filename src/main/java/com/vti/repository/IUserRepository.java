package com.vti.repository;

import com.vti.entity.Role;
import com.vti.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
    List<User> findByRole(Role role);
}
