
package com.dispute.controller;

import com.dispute.dto.Request.CreateDisputeRequest;
import com.dispute.dto.response.CreateDisputeResponse;
import com.dispute.dto.response.DisputeDetailsFetchedById;
import com.dispute.dto.response.DisputeResponse;
import com.dispute.dto.response.UpdateDisputeRequest;
import com.dispute.service.DisputeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.desktop.SystemEventListener;
import java.util.List;

@RestController
@RequestMapping("/api/disputes")


//How to add log to check respose time we nee to add

@Slf4j

public class DisputeController {

    private final DisputeService disputeService;
    public DisputeController(DisputeService disputeService) {
        this.disputeService = disputeService;
    }

    @PostMapping()
    public ResponseEntity<CreateDisputeResponse> createDispute(
            @Valid @RequestBody CreateDisputeRequest request) {

        //For LOG checking how much tim it is taking

        long start=System.currentTimeMillis();
        log.info("Create dispute request started");

        CreateDisputeResponse response =
                disputeService.createDispute(request);

        //Cheking how much time it is taking

        long timeTaken= System.currentTimeMillis();

          long  takenTime=start-timeTaken;
        log.info("Time taken to responsde :"+takenTime);



        return new ResponseEntity<CreateDisputeResponse>(
                response,
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisputeDetailsFetchedById> getDisputeById(
            @PathVariable   Long id) {

        DisputeDetailsFetchedById response =
                disputeService.getDisputeById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<DisputeResponse>>
    getAllDisputes() {

        return ResponseEntity.ok(
                disputeService.getAllDisputes());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisputeResponse>
    updateDispute(
            @PathVariable Long id,
            @Valid @RequestBody UpdateDisputeRequest request) {

        DisputeResponse response =
                disputeService.updateDispute(
                        id,
                        request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>
    deleteDispute(
            @PathVariable Long id) {

        disputeService.deleteDispute(id);

        return ResponseEntity.ok(
                "Dispute Deleted Successfully");
    }
}
