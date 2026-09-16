package com.dispute.client;


import com.dispute.dto.response.UserSummaryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(
        name = "USER-SERVICE",
        url = "http://localhost:8081"
)
public interface UserServiceClient {

    @GetMapping("/users/{id}/summary")
    UserSummaryResponse getUserSummary(@PathVariable("id") Long id);


}
