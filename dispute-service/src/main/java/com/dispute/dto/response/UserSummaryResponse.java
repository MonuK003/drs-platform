package com.dispute.dto.response;

import lombok.Data;

@Data
public class UserSummaryResponse {

    private Long id;

    //private String employeeId;

    private String fullName;

    private String email;

    private String status;
}