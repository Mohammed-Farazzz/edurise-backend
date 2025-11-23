package com.edurise.EduRise.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentProfileResponse {
    private Long id;
    private String fullName;
    private String email;
    private String institution;
    private String course;
    private String backgroundInfo;
    private Double neededAmount;
    private String status;
}
