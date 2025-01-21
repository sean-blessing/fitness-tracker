package com.ft.db;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ft.model.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	public Optional<User> findByEmailAndPassword(String email, String password);

}
