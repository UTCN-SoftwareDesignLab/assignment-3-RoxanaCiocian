package com.example.demo.consultation.dto;

import com.example.demo.patient.model.Patient;
import com.example.demo.user.model.User;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ConsultationDTO {
    private Long id;
    private Long patientId;
    private Long doctorId;
    private LocalDateTime date;
    private String description;
}
