package com.dispute.service;

import com.dispute.client.TransactionClient;
import com.dispute.client.UserServiceClient;
import com.dispute.dto.Request.CreateDisputeRequest;
import com.dispute.dto.Request.TransactionValidationRequest;
import com.dispute.dto.response.*;
import com.dispute.entity.Dispute;
import com.dispute.exception.ResourceNotFoundException;
import com.dispute.exception.UserNotFoundException;
import com.dispute.exception.UserServiceUnavailableException;
import com.dispute.repository.DisputeRepository;
import feign.RetryableException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DisputeServiceImpl implements DisputeService {

    private final DisputeRepository disputeRepository;
    private final UserServiceClient userServiceClient;
    private  final TransactionClient transactionClient;

    public DisputeServiceImpl(DisputeRepository disputeRepository,
                              UserServiceClient userServiceClient,TransactionClient transactionClient) {
        this.disputeRepository = disputeRepository;
        this.userServiceClient = userServiceClient;
        this.transactionClient = transactionClient;
    }

    @Override
    public  CreateDisputeResponse createDispute(CreateDisputeRequest request) {

        // 1. Validate customer for BankCustomerServices

        //this other services whcih could be down
         UserSummaryResponse user;
        try {
             user =
                    userServiceClient.getUserSummary(request.getCustomerId());
        }catch(RetryableException ex) {

            throw new UserServiceUnavailableException("Services is Unavilable",ex);

        }


          if(user == null){
              throw new UserNotFoundException("Please Enter the valid user Name");
          }

     //Fetching the Details from the DisputeRequest for validating Transaction

        TransactionValidationRequest transactionValidationRequest=new TransactionValidationRequest();
        transactionValidationRequest.setCustomerId(request.getCustomerId());
        transactionValidationRequest.setTransactionId(request.getTransactionId());

        //Getting the details From the Transaction services_for validation

        TransactionValidationResponse transactionValidationResponse=transactionClient. validate(transactionValidationRequest);


        // 3. Check validation for transaction
        if (!transactionValidationResponse.isValid()) {
            throw new RuntimeException(
                    "Transaction validation failed: "
                            + transactionValidationResponse.getMessage()
            );
        }

       //Creating the dispute through user Information

        Dispute dispute = new Dispute();

       // Map the reqeust to Entity

        dispute.setTransactionId(request.getTransactionId());
        dispute.setReason(request.getReason());
        dispute.setDescription(request.getDescription());
        dispute.setDisputeType(request.getDisputeType());
        dispute.setCustomerId(user.getId());
        dispute.setTitle(request.getTitle());

        Dispute savedDispute = disputeRepository.save(dispute);

// Entity to response

         CreateDisputeResponse disputeResponse=new CreateDisputeResponse();
        disputeResponse.setDisputeId(savedDispute.getDisputeId());
         disputeResponse.setStatus(String.valueOf(savedDispute.getDisputeType()));
         disputeResponse.setMessage("Congratulations! your Dispute has been created");

         return disputeResponse ;

    }

    @Override
    public DisputeDetailsFetchedById getDisputeById(Long id) {

        Dispute dispute = disputeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Dispute not found with id : " + id));

        UserSummaryResponse user =
                userServiceClient.getUserSummary(dispute.getCustomerId());


        // Returning  Value to Dto level what ever we want to Declare

                  DisputeDetailsFetchedById disputeResponse1= new DisputeDetailsFetchedById();

                     disputeResponse1.setDisputeStatus(String.valueOf(dispute.getDisputeStatus()));
                     disputeResponse1.setDisputeId(Collections.singletonList(dispute.getDisputeId()));
                     disputeResponse1.setCustomerId(user.getId());

                     return disputeResponse1;

//        return convertToResponse(dispute, user);
    }

    @Override
    public List<DisputeResponse> getAllDisputes() {

        return disputeRepository.findAll()
                .stream()
                .map(dispute -> {

                    UserSummaryResponse user =
                            userServiceClient.getUserSummary(dispute.getCustomerId());

                    return convertToResponse(dispute, user);

                })
                .collect(Collectors.toList());
    }

    @Override
    public DisputeResponse updateDispute(Long id,
                                         UpdateDisputeRequest request) {

        Dispute dispute = disputeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Dispute not found with id : " + id));

        dispute.setTransactionId(request.getTransactionId());
        dispute.setReason(request.getReason());
        dispute.setDescription(request.getDescription());

        Dispute updatedDispute = disputeRepository.save(dispute);

        UserSummaryResponse user =
                userServiceClient.getUserSummary(updatedDispute.getCustomerId());

        return convertToResponse(updatedDispute, user);
    }

    @Override
    public void deleteDispute(Long id) {

        Dispute dispute = disputeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Dispute not found with id : " + id));

        disputeRepository.delete(dispute);
    }

    private DisputeResponse convertToResponse(Dispute dispute,
                                              UserSummaryResponse user) {

        DisputeResponse response = new DisputeResponse();

        response.setId(dispute.getId());
        response.setTransactionId(dispute.getTransactionId());
        response.setReason(dispute.getReason());
        response.setDescription(dispute.getDescription());
        response.setDisputeStatus(String.valueOf(dispute.getDisputeType()));
        response.setCreatedAt(dispute.getCreatedAt());

        response.setCustomerId(user.getId());
        // response.setEmployeeId(user.getEmployeeId());
       // response.setUserName(user.getFullName());
       // response.setUserEmail(user.getEmail());
        response.setTitle(dispute.getTitle());


        response.setCreatedAt(dispute.getCreatedAt());
        response.setUpdatedAt(dispute.getUpdatedAt());


        return response;
    }





}