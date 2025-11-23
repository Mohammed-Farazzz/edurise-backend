package com.edurise.EduRise.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentProfileRequest {
    private String fullName;    // Add full name field
    private String dob;         // Add date of birth
    private String phone;       // Add phone
    private String email;       // Add email
    private String institution;
    private String course;
    private String backgroundInfo;
    private String grade;       // Add grade field
    private Double neededAmount;
}
