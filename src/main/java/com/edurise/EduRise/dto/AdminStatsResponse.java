package com.edurise.EduRise.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminStatsResponse {
    private long pendingCount;
    private long approvedCount;
    private long totalDonations;
    private long fundedCount;
}
