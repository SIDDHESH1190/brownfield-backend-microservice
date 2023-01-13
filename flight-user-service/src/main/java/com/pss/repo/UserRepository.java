package com.pss.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pss.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	// Get user by email
	public Optional<User> findByEmailId(String emailId);
}
