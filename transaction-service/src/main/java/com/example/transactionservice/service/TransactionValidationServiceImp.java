package com.example.transactionservice.service;

import com.example.transactionservice.Entity.Transaction;
import com.example.transactionservice.Enum.Status;
import com.example.transactionservice.Repository.TransactionRepository;
import com.example.transactionservice.dto.TransactionIsCreated;
import com.example.transactionservice.dto.TransactionCreationRequest;
import com.example.transactionservice.dto.TransactionValidationRequest;
import com.example.transactionservice.dto.TransactionValidationResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Deterministic, in-memory stand-in for the real Transaction Service's
 * validation logic. No database, no external calls - just simple rules
 * keyed off transactionId so that the SAME request always produces the
 * SAME response. That predictability is the whole point: it lets you
 * write repeatable integration tests against the Dispute Service.
 *
 * Convention used by this mock (document this for your team/README):
 *   - transactionId starting with "NF"  -> not found
 *   - transactionId starting with "DUP" -> already disputed
 *   - transactionId starting with "OLD" -> found, but outside dispute window
 *   - anything else                     -> valid and eligible
 */


    @Service
    public class TransactionValidationServiceImp implements  TransactionValidationServices {

        private final TransactionRepository transactionRepository;

            TransactionValidationServiceImp(TransactionRepository transactionRepository){
            this.transactionRepository=transactionRepository;
        }



    public TransactionValidationResponse validate(
                TransactionValidationRequest request) {

            String transactionId = request.getTransactionId();
            Long customerId = request.getCustomerId();

            Optional<Transaction> transactionOptional =
                    transactionRepository.findByTransactionId(transactionId);

            // Transaction does not exist
            if (transactionOptional.isEmpty()) {
                return TransactionValidationResponse.builder()
                        .transactionId(transactionId)
                        .valid(false)
                        .message("Transaction not found")
                        .build();
            }

            Transaction transaction = transactionOptional.get();

            // Transaction exists but belongs to another customer
            if (!transaction.getCustomerId().equals(customerId)) {
                return TransactionValidationResponse.builder()
                        .transactionId(transactionId)
                        .valid(false)
                        .customerId(transaction.getCustomerId())
                        .amount(transaction.getAmount())
                        .status(String.valueOf(transaction.getStatus()))
                        .message("Transaction does not belong to customer")
                        .build();
            }

            // Transaction is valid
            return TransactionValidationResponse.builder()
                    .transactionId(transaction.getTransactionId())
                    .valid(true)
                    .customerId(transaction.getCustomerId())
                    .amount(transaction.getAmount())
                    .status(String.valueOf(transaction.getStatus()))
                    .message("Transaction is valid")
                    .build();
        }

               public TransactionIsCreated createTransaction(TransactionCreationRequest request){

                      Transaction transaction = new Transaction();

                      //Saving the details to data base
                       transaction.setTransactionId(request.getTransactionId());
                       transaction.setCustomerId(request.getCustomerId());
                       transaction.setStatus(Status.valueOf(String.valueOf(request.getStatus())));
                       Transaction savedTransaction=transactionRepository.save(transaction);

           //Mapping to DTO

                      TransactionIsCreated transactionIsCreated=new TransactionIsCreated();

                      transactionIsCreated.setMessage("Transaction is created");

                      return  transactionIsCreated;


               }


    }

