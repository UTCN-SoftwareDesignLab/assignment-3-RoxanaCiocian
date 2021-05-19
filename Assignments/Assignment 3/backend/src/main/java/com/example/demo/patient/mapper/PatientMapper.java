package com.example.demo.patient.mapper;

import com.example.demo.patient.dto.PatientDTO;
import com.example.demo.patient.model.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientDTO toDto(Patient patient);

    Patient fromDto(PatientDTO dto);
}
