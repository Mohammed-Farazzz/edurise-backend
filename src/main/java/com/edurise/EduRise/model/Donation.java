package com.edurise.EduRise.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "donations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Donor who made the donation
    @ManyToOne
    @JoinColumn(name = "donor_user_id", referencedColumnName = "userId")
    private User donor;

    // Student receiving the donation
    @ManyToOne
    @JoinColumn(name = "student_user_id", referencedColumnName = "userId")
    private User student;

    private Double amount;

    private LocalDateTime donatedAt;

    private String status; // e.g., "PENDING", "COMPLETED"
    
 // In Donation.java
    private String disbursementStatus; // "PENDING", "DISBURSED", "FAILED"

}
