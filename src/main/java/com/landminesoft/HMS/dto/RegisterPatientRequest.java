package com.landminesoft.HMS.dto;

import com.landminesoft.HMS.entity.Gender;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterPatientRequest {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotNull
    private Integer age;

    @NotNull
    private Gender gender;

    @NotBlank
    private String phone;

    private String address;

    private String bloodGroup;

    private String emergencyContact;
}
