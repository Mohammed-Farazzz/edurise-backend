package com.edurise.EduRise.repository;

import com.edurise.EduRise.model.DonorProfile;
import com.edurise.EduRise.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DonorProfileRepository extends JpaRepository<DonorProfile, Long> {
    Optional<DonorProfile> findByUser(User user);
}
