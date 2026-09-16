package com.example.userservice.repository;

import com.example.userservice.entity.User;
import com.example.userservice.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by email
     */
    Optional<User> findByEmail(String email);

    /**
     * Find user by employeeId
     */
    Optional<User> findByEmployeeId(String employeeId);

    /**
     * Check duplicate email
     */
    boolean existsByEmail(String email);

    /**
     * Check duplicate employeeId
     */
    boolean existsByEmployeeId(String employeeId);

    /**
     * Get all active users
     */
    List<User> findByStatus(UserStatus status);

    /**
     * Get active users with pagination
     */
    Page<User> findByStatus(UserStatus status, Pageable pageable);

    /**
     * Search by first name
     */
    List<User> findByFirstNameContainingIgnoreCase(String firstName);

    /**
     * Search by last name
     */
    List<User> findByLastNameContainingIgnoreCase(String lastName);

    /**
     * Search by email
     */
    List<User> findByEmailContainingIgnoreCase(String email);

    /**
     * Search by first name or last name
     */
    List<User> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName,
            String lastName);

    /**
     * Find active user by email
     */
    Optional<User> findByEmailAndStatus(String email, UserStatus status);

}