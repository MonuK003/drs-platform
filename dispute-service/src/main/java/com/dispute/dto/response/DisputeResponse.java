package com.dispute.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisputeResponse {


    private String disputeId;

    private String transactionId;
    private String disputeStatus;
    private Long customerId;
    private Long id;
    private String title;
    private String reason;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}