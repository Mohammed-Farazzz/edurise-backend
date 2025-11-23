package com.edurise.EduRise.repository;

import com.edurise.EduRise.model.StudentProfile;
import com.edurise.EduRise.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {
    Optional<StudentProfile> findByUser(User user);
}
