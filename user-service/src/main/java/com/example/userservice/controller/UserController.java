
package com.example.userservice.controller;

import com.example.userservice.dto.CreateUserRequest;
import com.example.userservice.dto.UpdateUserRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.dto.UserSummaryResponse;
import com.example.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Create User
     */
    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody CreateUserRequest request) {

        UserResponse response = userService.createUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Get All Users
     */
    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAllUsers(Pageable pageable) {

        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    /**
     * Get User By Id
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable Long id) {

        return ResponseEntity.ok(userService.getUserById(id));
    }

    /**
     * Update User
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request) {

        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    /**
     * Delete User
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id) {

        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Used by other microservices
     */
    @GetMapping("/{id}/summary")
    public ResponseEntity<UserSummaryResponse> getUserSummary(
            @PathVariable Long id) {

        return ResponseEntity.ok(userService.getUserSummary(id));
    }

    /**
     * Used by other microservices
     */
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> userExists(
            @PathVariable Long id) {

        return ResponseEntity.ok(userService.existsById(id));
    }

    /**
     * Block User
     */
    @PutMapping("/{id}/block")
    public ResponseEntity<UserResponse> blockUser(
            @PathVariable Long id) {

        return ResponseEntity.ok(userService.blockUser(id));
    }

    /**
     * Activate User
     /{id}/activate

     **/

}
