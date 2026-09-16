
package com.dispute.service;

import com.dispute.dto.Request.CreateDisputeRequest;
import com.dispute.dto.response.CreateDisputeResponse;
import com.dispute.dto.response.DisputeDetailsFetchedById;
import com.dispute.dto.response.DisputeResponse;
import com.dispute.dto.response.UpdateDisputeRequest;

import java.util.List;

public interface DisputeService {

    CreateDisputeResponse createDispute(CreateDisputeRequest request);

    DisputeDetailsFetchedById getDisputeById(Long id);

    List<DisputeResponse> getAllDisputes();

    DisputeResponse updateDispute(Long id,
                                  UpdateDisputeRequest request);

    void deleteDispute(Long id);
}