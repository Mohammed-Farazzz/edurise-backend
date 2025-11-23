package com.edurise.EduRise.controller;

import com.edurise.EduRise.dto.StudentProfileResponse;
import com.edurise.EduRise.dto.DonationResponse;
import com.edurise.EduRise.dto.AdminStatsResponse;
import com.edurise.EduRise.service.StudentProfileService;
import com.edurise.EduRise.service.DonationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {

    private final StudentProfileService studentProfileService;
    private final DonationService donationService;

    public AdminController(StudentProfileService studentProfileService,
                           DonationService donationService) {
        this.studentProfileService = studentProfileService;
        this.donationService = donationService;
    }

    // ==================== STUDENT REQUEST MANAGEMENT ====================

    // ✅ List all pending student requests
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/students/pending")
    public ResponseEntity<List<StudentProfileResponse>> getPendingStudents() {
        return ResponseEntity.ok(studentProfileService.getPendingStudentProfiles());
    }

    // ✅ Update status of a student (approve/reject)
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/students/{id}/status")
    public ResponseEntity<StudentProfileResponse> updateStudentStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return ResponseEntity.ok(studentProfileService.updateStudentRequestStatus(id, status));
    }

    // ✅ List all approved students
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/students/approved")
    public ResponseEntity<List<StudentProfileResponse>> getApprovedStudents() {
        return ResponseEntity.ok(studentProfileService.getAllApprovedStudents());
    }

    // ==================== DONATION MANAGEMENT ====================

    // ✅ List pending disbursements
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/donations/pending")
    public ResponseEntity<List<DonationResponse>> getPendingDonations() {
        return ResponseEntity.ok(donationService.getPendingDisbursements());
    }

    // ✅ Mark donation as disbursed
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/donations/{id}/disburse")
    public ResponseEntity<DonationResponse> disburseDonation(@PathVariable Long id) {
        return ResponseEntity.ok(donationService.markDisbursed(id));
    }

    // ✅ List all donations (history)
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/donations/all")
    public ResponseEntity<List<DonationResponse>> getAllDonations() {
        return ResponseEntity.ok(donationService.getAllDonations());
    }

    // ==================== DONOR LIST ====================
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/donors/all")
    public ResponseEntity<List<DonationResponse>> getAllDonors() {
        return ResponseEntity.ok(donationService.getAllDonations());
    }

    // ==================== ADMIN STATS ====================
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/stats")
    public ResponseEntity<AdminStatsResponse> getAdminStats() {
        long pendingCount = studentProfileService.getPendingStudentProfiles().size();
        long approvedCount = studentProfileService.countApprovedStudents();
        long totalDonations = donationService.getTotalDonations();
        long fundedCount = donationService.countFundedStudents();

        AdminStatsResponse stats = AdminStatsResponse.builder()
                .pendingCount(pendingCount)
                .approvedCount(approvedCount)
                .totalDonations(totalDonations)
                .fundedCount(fundedCount)
                .build();

        return ResponseEntity.ok(stats);
    }
}
