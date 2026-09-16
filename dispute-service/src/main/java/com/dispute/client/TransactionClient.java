package com.dispute.client;

import com.dispute.dto.Request.TransactionValidationRequest;
import com.dispute.dto.response.TransactionValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@FeignClient(
        name = "transaction-client",
        url = "http://localhost:8084"
)
public interface TransactionClient {

    @PostMapping("/transactions/validate-for-dispute")
    TransactionValidationResponse validate(
            @RequestBody TransactionValidationRequest request
    );
}
