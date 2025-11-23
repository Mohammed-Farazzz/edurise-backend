package com.edurise.EduRise.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "student_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link profile to User (each student user has one profile)
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;
    private String fullName;  // add this
    private LocalDate dob;    // add this
    private String phone;     // add this
    private String email;     // add this
    private String institution;
    private String course;
    private String backgroundInfo;
    private String grade;
    private Double neededAmount;    
    private String status; // e.g., "PENDING", "APPROVED"
}
