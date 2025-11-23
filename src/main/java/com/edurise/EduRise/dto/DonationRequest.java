package com.edurise.EduRise.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonationRequest {
    private Long studentUserId; // The student to whom donor wants to donate
    private Double amount;
}
