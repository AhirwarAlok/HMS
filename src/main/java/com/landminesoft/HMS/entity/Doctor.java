package com.landminesoft.HMS.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.mapping.List;

import java.util.ArrayList;

@Data
@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String specialization;

    private boolean available = true;

    @OneToMany(mappedBy = "doctor")
    private ArrayList<Appointment> appointments = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}
