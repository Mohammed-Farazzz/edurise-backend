package com.edurise.EduRise.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    private String fullName;
    private String email;
    private String password;
    private String role;  // We'll map this String to your Role enum later
}
