package com.edurise.EduRise.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "donor_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonorProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link profile to User (one-to-one relation)
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    private String organization;
    private String contactNumber;
    private String address;
    private String description;
}
