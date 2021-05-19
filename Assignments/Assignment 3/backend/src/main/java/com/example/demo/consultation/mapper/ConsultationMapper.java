package com.example.demo.consultation.mapper;

import com.example.demo.consultation.dto.ConsultationDTO;
import com.example.demo.consultation.model.Consultation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface ConsultationMapper {

    @Mappings({
            @Mapping(target="patientId", expression = "java(consultation.getPatientId().getId())"),
            @Mapping(target="doctorId", expression = "java(consultation.getDoctorId().getId())"),
    })
    ConsultationDTO toDto(Consultation consultation);

    @Mappings({
            @Mapping(target = "patientId.id", source = "patientId"),
            @Mapping(target = "doctorId.id", source = "doctorId"),
    })
    Consultation fromDto(ConsultationDTO consultationDto);
}
