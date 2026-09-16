package com.example.userservice.dto;

import com.example.userservice.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSummaryResponse {

    private Long id;

    private String employeeId;

    private String fullName;

    private String email;

    private UserStatus status;
}