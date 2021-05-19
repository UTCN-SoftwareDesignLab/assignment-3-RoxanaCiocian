package com.example.demo.consultation.model;


import com.example.demo.patient.model.Patient;
import com.example.demo.user.model.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patientId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User doctorId;

    private LocalDateTime date;

    @Column(nullable = false, length = 512)
    private String description;
}
