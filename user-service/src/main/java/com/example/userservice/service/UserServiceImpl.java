package com.example.userservice.service;

import com.example.userservice.dto.CreateUserRequest;
import com.example.userservice.dto.UpdateUserRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.dto.UserSummaryResponse;
import com.example.userservice.entity.User;
import com.example.userservice.enums.UserStatus;
import com.example.userservice.exception.ResourceNotFoundException;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse createUser(CreateUserRequest request) {

        User user = new User();

        user.setEmployeeId(request.getEmployeeId());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setStatus(UserStatus.ACTIVE);

        User savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
    }

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {

        return userRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Override
    public UserResponse getUserById(Long id) {

        User user = findUser(id);

        return mapToResponse(user);
    }

    @Override
    public UserResponse updateUser(Long id,
                                   UpdateUserRequest request) {

        User user = findUser(id);

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        User updated = userRepository.save(user);

        return mapToResponse(updated);
    }

    @Override
    public void deleteUser(Long id) {

        User user = findUser(id);

        userRepository.delete(user);
    }

    @Override
    public UserSummaryResponse getUserSummary(Long id) {

        User user = findUser(id);

        UserSummaryResponse response = new UserSummaryResponse();

        response.setId(user.getId());
        response.setFullName(user.getFirstName() + " " + user.getLastName());
        response.setEmail(user.getEmail());
        response.setStatus(user.getStatus());

        return response;
    }

    @Override
    public Boolean existsById(Long id) {

        return userRepository.existsById(id);
    }

    @Override
    public UserResponse blockUser(Long id) {

        User user = findUser(id);

        user.setStatus(UserStatus.BLOCKED);

        return mapToResponse(userRepository.save(user));
    }

    @Override
    public UserResponse activateUser(Long id) {

        User user = findUser(id);

        user.setStatus(UserStatus.ACTIVE);

        return mapToResponse(userRepository.save(user));
    }

    /**
     * Common Method
     */
    private User findUser(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id : " + id));
    }

    /**
     * Entity -> DTO
     */
    private UserResponse mapToResponse(User user) {

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setEmployeeId(user.getEmployeeId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setStatus(user.getStatus());

        return response;
    }
}