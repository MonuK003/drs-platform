package com.dispute.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateDisputeRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Description is required")
    private String transactionId;

    @NotBlank(message = "Reason should be valid")
    private String reason;
}
