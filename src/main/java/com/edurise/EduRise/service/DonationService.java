package com.edurise.EduRise.service;

import com.edurise.EduRise.dto.DonationRequest;
import com.edurise.EduRise.dto.DonationResponse;
import com.edurise.EduRise.model.Donation;
import com.edurise.EduRise.model.User;
import com.edurise.EduRise.repository.DonationRepository;
import com.edurise.EduRise.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonationService {

    private final DonationRepository donationRepository;
    private final UserRepository userRepository;

    public DonationService(DonationRepository donationRepository, UserRepository userRepository) {
        this.donationRepository = donationRepository;
        this.userRepository = userRepository;
    }

    // Make a donation
    public DonationResponse makeDonation(DonationRequest request) {
        String donorEmail = getCurrentUserEmail();
        User donor = userRepository.findByEmail(donorEmail)
                .orElseThrow(() -> new RuntimeException("Donor user not found"));

        User student = userRepository.findById(request.getStudentUserId())
                .orElseThrow(() -> new RuntimeException("Student user not found"));

        Donation donation = Donation.builder()
                .donor(donor)
                .student(student)
                .amount(request.getAmount())
                .donatedAt(LocalDateTime.now())
                .status("COMPLETED") // For simplicity, mark as completed immediately
                .disbursementStatus("PENDING")
                .build();

        donationRepository.save(donation);

        return mapToDonationResponse(donation);
    }

    // Get donation history of the logged-in donor
    public List<DonationResponse> getDonationHistory() {
        String donorEmail = getCurrentUserEmail();
        User donor = userRepository.findByEmail(donorEmail)
                .orElseThrow(() -> new RuntimeException("Donor user not found"));

        return donationRepository.findByDonor(donor).stream()
                .map(this::mapToDonationResponse)
                .collect(Collectors.toList());
    }
 // List all donations with PENDING disbursement
    public List<DonationResponse> getPendingDisbursements() {
        return donationRepository.findAll().stream()
            .filter(donation -> "PENDING".equalsIgnoreCase(donation.getDisbursementStatus()))
            .map(this::mapToDonationResponse)
            .toList();
    }

    // Mark donation as DISBURSED
    public DonationResponse markDisbursed(Long donationId) {
        Donation donation = donationRepository.findById(donationId)
            .orElseThrow(() -> new RuntimeException("Donation not found"));
        donation.setDisbursementStatus("DISBURSED");
        donationRepository.save(donation);
        return mapToDonationResponse(donation);
    }
    public List<DonationResponse> getAllDonations() {
        return donationRepository.findAll().stream()
            .map(this::mapToDonationResponse)
            .toList();
    }
 // Total donation amount
    public long getTotalDonations() {
        return (long) donationRepository.findAll().stream()
                .mapToDouble(Donation::getAmount)
                .sum();
    }

    // Count funded students (students with at least one disbursed donation)
    public long countFundedStudents() {
        return donationRepository.findAll().stream()
                .filter(d -> "DISBURSED".equalsIgnoreCase(d.getDisbursementStatus()))
                .map(d -> d.getStudent().getEmail()) // email as unique identifier
                .distinct()
                .count();
    }




    private DonationResponse mapToDonationResponse(Donation donation) {
        return DonationResponse.builder()
                .id(donation.getId())
                .donorName(donation.getDonor().getFullName())
                .studentName(donation.getStudent().getFullName())
                .amount(donation.getAmount())
                .donatedAt(donation.getDonatedAt())
                .status(donation.getStatus())
                .build();
    }

    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
