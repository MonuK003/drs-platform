package com.dispute.dto.Request;

import lombok.Data;

@Data
public class TransactionValidationRequest {


        private String transactionId;
        private Long customerId;

    }

