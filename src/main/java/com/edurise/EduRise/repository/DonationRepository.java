package com.edurise.EduRise.repository;

import com.edurise.EduRise.model.Donation;
import com.edurise.EduRise.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByDonor(User donor);
    List<Donation> findByStudent(User student);
}
