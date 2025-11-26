package com.taskTrackerManager.TaskTracker.repository;

import com.taskTrackerManager.TaskTracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * User Repository - Database operations for User
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by email
     * @param email User email
     * @return Optional user (empty if not found)
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if user exists by email
     * @param email User email
     * @return true if exists, false otherwise
     */
    boolean existsByEmail(String email);
}
