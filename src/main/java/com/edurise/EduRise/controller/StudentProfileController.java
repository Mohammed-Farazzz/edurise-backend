package com.edurise.EduRise.controller;

import com.edurise.EduRise.dto.StudentProfileRequest;
import com.edurise.EduRise.dto.StudentProfileResponse;
import com.edurise.EduRise.service.StudentProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins="http://localhost:3000")
public class StudentProfileController {

    private final StudentProfileService studentProfileService;

    public StudentProfileController(StudentProfileService studentProfileService) {
        this.studentProfileService = studentProfileService;
    }

    // Only users with STUDENT role can apply/update their profile
    @PreAuthorize("hasAuthority('STUDENT')")
    @PostMapping("/details")
    public ResponseEntity<StudentProfileResponse> applyOrUpdate(@RequestBody StudentProfileRequest request) {
        StudentProfileResponse response = studentProfileService.createOrUpdateProfile(request);
        return ResponseEntity.ok(response);
    }

    // Fetch own profile
    @PreAuthorize("hasAuthority('STUDENT')")
    @GetMapping("/me")
    public ResponseEntity<StudentProfileResponse> getMyProfile() {
        StudentProfileResponse response = studentProfileService.getOwnProfile();
        return ResponseEntity.ok(response);
    }
}
