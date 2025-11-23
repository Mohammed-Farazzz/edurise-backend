package com.edurise.EduRise.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonationResponse {
    private Long id;
    private String donorName;
    private String studentName;
    private Double amount;
    private LocalDateTime donatedAt;
    private String status;
}
