package com.edurise.EduRise.controller;

import com.edurise.EduRise.dto.DonationRequest;
import com.edurise.EduRise.dto.DonationResponse;
import com.edurise.EduRise.service.DonationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donations")
@CrossOrigin
public class DonationController {

    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    // Endpoint for donors to make donations
    @PreAuthorize("hasAuthority('DONOR')")
    @PostMapping
    public ResponseEntity<DonationResponse> donate(@RequestBody DonationRequest request) {
        DonationResponse response = donationService.makeDonation(request);
        return ResponseEntity.ok(response);
    }

    // Endpoint to get the logged-in donor's donation history
    @PreAuthorize("hasAuthority('DONOR')")
    @GetMapping("/history")
    public ResponseEntity<List<DonationResponse>> getDonationHistory() {
        List<DonationResponse> donations = donationService.getDonationHistory();
        return ResponseEntity.ok(donations);
    }
}
