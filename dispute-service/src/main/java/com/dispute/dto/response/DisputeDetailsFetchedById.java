package com.dispute.dto.response;

import lombok.Data;

import java.util.List;
@Data
public class DisputeDetailsFetchedById {

       private Long customerId;
       private String disputeStatus;
       private List<String> disputeId;

 

}
