package com.edurise.EduRise.controller;

import com.edurise.EduRise.dto.DonorProfileRequest;
import com.edurise.EduRise.dto.DonorProfileResponse;
import com.edurise.EduRise.dto.StudentProfileResponse;
import com.edurise.EduRise.service.DonorProfileService;
import com.edurise.EduRise.service.StudentProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donors")
@CrossOrigin
public class DonorProfileController {

    private final DonorProfileService donorProfileService;
    private final StudentProfileService studentProfileService;

    public DonorProfileController(DonorProfileService donorProfileService, StudentProfileService studentProfileService) {
        this.donorProfileService = donorProfileService;
        this.studentProfileService = studentProfileService;
    }

    // Create or update donor profile
    @PreAuthorize("hasAuthority('DONOR')")
    @PostMapping("/profile")
    public ResponseEntity<DonorProfileResponse> createOrUpdateProfile(@RequestBody DonorProfileRequest request) {
        DonorProfileResponse response = donorProfileService.createOrUpdateProfile(request);
        return ResponseEntity.ok(response);
    }

    // Get donor’s own profile
    @PreAuthorize("hasAuthority('DONOR')")
    @GetMapping("/me")
    public ResponseEntity<DonorProfileResponse> getMyProfile() {
        DonorProfileResponse response = donorProfileService.getOwnProfile();
        return ResponseEntity.ok(response);
    }

    // List all approved student profiles needing help (for donors)
    @PreAuthorize("hasAuthority('DONOR')")
    @GetMapping("/students")
    public ResponseEntity<List<StudentProfileResponse>> getAllNeedyStudents() {
        List<StudentProfileResponse> students = studentProfileService.getAllApprovedStudents();
        return ResponseEntity.ok(students);
    }
}
