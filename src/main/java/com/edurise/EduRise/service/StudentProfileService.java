package com.edurise.EduRise.service;

import com.edurise.EduRise.dto.StudentProfileRequest;
import java.util.List;

import com.edurise.EduRise.dto.StudentProfileResponse;
import com.edurise.EduRise.model.StudentProfile;
import com.edurise.EduRise.model.User;
import com.edurise.EduRise.repository.StudentProfileRepository;
import com.edurise.EduRise.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class StudentProfileService {

    private final StudentProfileRepository studentProfileRepository;
    private final UserRepository userRepository;

    public StudentProfileService(StudentProfileRepository studentProfileRepository, UserRepository userRepository) {
        this.studentProfileRepository = studentProfileRepository;
        this.userRepository = userRepository;
    }
    public List<StudentProfileResponse> getAllApprovedStudents() {
        return studentProfileRepository.findAll().stream()
                .filter(profile -> "APPROVED".equalsIgnoreCase(profile.getStatus()))
                .map(profile -> StudentProfileResponse.builder()
                        .id(profile.getId())
                        .fullName(profile.getUser().getFullName())
                        .email(profile.getUser().getEmail())
                        .institution(profile.getInstitution())
                        .course(profile.getCourse())
                        .backgroundInfo(profile.getBackgroundInfo())
                        .neededAmount(profile.getNeededAmount())
                        .status(profile.getStatus())
                        .build())
                .toList();
    }
 // Get all student profiles with status "PENDING"
    public List<StudentProfileResponse> getPendingStudentProfiles() {
        return studentProfileRepository.findAll().stream()
            .filter(profile -> "PENDING".equalsIgnoreCase(profile.getStatus()))
            .map(profile -> StudentProfileResponse.builder()
                    .id(profile.getId())
                    .fullName(profile.getUser().getFullName())
                    .email(profile.getUser().getEmail())
                    .institution(profile.getInstitution())
                    .course(profile.getCourse())
                    .backgroundInfo(profile.getBackgroundInfo())
                    .neededAmount(profile.getNeededAmount())
                    .status(profile.getStatus())
                    .build())
            .toList();
    }

    // Approve or reject a student profile by ID
    public StudentProfileResponse updateStudentRequestStatus(Long profileId, String newStatus) {
        StudentProfile profile = studentProfileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Student profile not found"));

        profile.setStatus(newStatus.toUpperCase());
        studentProfileRepository.save(profile);

        User user = profile.getUser();

        return StudentProfileResponse.builder()
                .id(profile.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .institution(profile.getInstitution())
                .course(profile.getCourse())
                .backgroundInfo(profile.getBackgroundInfo())
                .neededAmount(profile.getNeededAmount())
                .status(profile.getStatus())
                .build();
    }


    // Create or update student profile
    public StudentProfileResponse createOrUpdateProfile(StudentProfileRequest request) {
        String email = getCurrentUserEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));


            StudentProfile profile = studentProfileRepository.findByUser(user)
                    .orElse(StudentProfile.builder().user(user).status("PENDING").build());

            profile.setFullName(request.getFullName());
            profile.setDob(LocalDate.parse(request.getDob()));   // parse from String
            profile.setPhone(request.getPhone());
            profile.setEmail(request.getEmail());
            profile.setInstitution(request.getInstitution());
            profile.setCourse(request.getCourse());
            profile.setBackgroundInfo(request.getBackgroundInfo());
            profile.setGrade(request.getGrade());
            profile.setNeededAmount(request.getNeededAmount());

            studentProfileRepository.save(profile);

            return StudentProfileResponse.builder()
                    .id(profile.getId())
                    .fullName(user.getFullName())
                    .email(user.getEmail())
                    .institution(profile.getInstitution())
                    .course(profile.getCourse())
                    .backgroundInfo(profile.getBackgroundInfo())
                    .neededAmount(profile.getNeededAmount())
                    .status(profile.getStatus())
                    .build();
        }

    // Fetch own profile
    public StudentProfileResponse getOwnProfile() {
        String email = getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        StudentProfile profile = studentProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        return StudentProfileResponse.builder()
                .id(profile.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .institution(profile.getInstitution())
                .course(profile.getCourse())
                .backgroundInfo(profile.getBackgroundInfo())
                .neededAmount(profile.getNeededAmount())
                .status(profile.getStatus())
                .build();
    }
 // Count approved students
    public long countApprovedStudents() {
        return studentProfileRepository.findAll().stream()
                .filter(profile -> "APPROVED".equalsIgnoreCase(profile.getStatus()))
                .count();
    }


    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
