package com.taskTrackerManager.TaskTracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User Entity - Represents a user in the database
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(name = "created_at")
    private Long createdAt;

    /**
     * Constructor for creating new user
     */
    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.createdAt = System.currentTimeMillis();
    }

    /**
     * Get user DTO for response (without password)
     */
    public UserDTO toDTO() {
        return new UserDTO(this.id, this.email, this.name);
    }
}

/**
 * User DTO - For API responses (no sensitive data)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
class UserDTO {
    private Long id;
    private String email;
    private String name;
}

/*
============================================
📝 EXPLANATION:
============================================

1. @Entity
   - Marks this as a JPA entity (database table)

2. @Table(name = "users")
   - Maps to database table "users"

3. @Id @GeneratedValue
   - Auto-increment primary key

4. @Column(unique = true)
   - Email must be unique

5. toDTO()
   - Converts User to DTO (without password)
   - Never expose password in responses!

============================================
*/