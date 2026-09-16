package com.dispute.dto.Request;


import com.dispute.enums.DisputeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateDisputeRequest {
    @NotBlank(message = "Transaction Id is required")
    private String transactionId;


    @NotBlank
    private String reason;


    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "User Id is required")
    private Long  customerId;

    @NotNull
    private DisputeType disputeType;


}
