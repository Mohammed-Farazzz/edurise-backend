package com.edurise.EduRise.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonorProfileRequest {
    private String organization;
    private String contactNumber;
    private String address;
    private String description;
}
