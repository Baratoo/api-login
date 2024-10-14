package com.example.api_login.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api_login.model.entities.User;

public interface IUserRepository extends JpaRepository<User, String>{

	Optional<User> findByEmail(String email);
}
