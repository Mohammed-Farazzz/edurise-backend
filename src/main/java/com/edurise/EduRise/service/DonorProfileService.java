package com.edurise.EduRise.service;

import com.edurise.EduRise.dto.DonorProfileRequest;
import com.edurise.EduRise.dto.DonorProfileResponse;
import com.edurise.EduRise.model.DonorProfile;
import com.edurise.EduRise.model.User;
import com.edurise.EduRise.repository.DonorProfileRepository;
import com.edurise.EduRise.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class DonorProfileService {

    private final DonorProfileRepository donorProfileRepository;
    private final UserRepository userRepository;

    public DonorProfileService(DonorProfileRepository donorProfileRepository, UserRepository userRepository) {
        this.donorProfileRepository = donorProfileRepository;
        this.userRepository = userRepository;
    }

    public DonorProfileResponse createOrUpdateProfile(DonorProfileRequest request) {
        String email = getCurrentUserEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        DonorProfile profile = donorProfileRepository.findByUser(user)
                .orElse(DonorProfile.builder().user(user).build());

        profile.setOrganization(request.getOrganization());
        profile.setContactNumber(request.getContactNumber());
        profile.setAddress(request.getAddress());
        profile.setDescription(request.getDescription());

        donorProfileRepository.save(profile);

        return DonorProfileResponse.builder()
                .id(profile.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .organization(profile.getOrganization())
                .contactNumber(profile.getContactNumber())
                .address(profile.getAddress())
                .description(profile.getDescription())
                .build();
    }

    public DonorProfileResponse getOwnProfile() {
        String email = getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        DonorProfile profile = donorProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Donor profile not found"));

        return DonorProfileResponse.builder()
                .id(profile.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .organization(profile.getOrganization())
                .contactNumber(profile.getContactNumber())
                .address(profile.getAddress())
                .description(profile.getDescription())
                .build();
    }

    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
