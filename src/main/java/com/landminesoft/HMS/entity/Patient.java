package com.landminesoft.HMS.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true, referencedColumnName = "id")
    private User user;
    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false, length = 15)
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false, length = 5)
    private String bloodGroup;

    @Column(length = 15)
    private String emergencyContact;

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments = new ArrayList<>();

}
