package com.edurise.EduRise.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonorProfileResponse {
    private Long id;
    private String fullName;
    private String email;
    private String organization;
    private String contactNumber;
    private String address;
    private String description;
}
