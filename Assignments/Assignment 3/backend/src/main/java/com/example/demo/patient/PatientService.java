package com.example.demo.patient;

import com.example.demo.patient.dto.PatientDTO;
import com.example.demo.patient.mapper.PatientMapper;
import com.example.demo.patient.model.Patient;
import com.example.demo.user.model.ERole;
import com.example.demo.user.model.Role;
import com.example.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public Patient findById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found: " + id));
    }

    public List<PatientDTO> findAllPatients() {
        return patientRepository.findAll().stream().
                map(patientMapper::toDto)
                .collect(Collectors.toList());
    }

    public Patient findPatientByFirstLastName(String firstName, String lastName){
        return patientRepository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with first name: " + firstName
                        + " and last name " + lastName));
    }

    public PatientDTO create(PatientDTO patientDto) {
        return patientMapper.toDto(patientRepository.save(
                patientMapper.fromDto(patientDto)));
    }

    public PatientDTO update(Long id, PatientDTO patientDto) {

        Patient patient = findById(id);

        patient.setFirstName(patientDto.getFirstName());
        patient.setLastName(patientDto.getLastName());
        patient.setIdNumber(patientDto.getIdNumber());
        patient.setCnp(patientDto.getCnp());
        patient.setBirthDate(patientDto.getBirthDate());
        patient.setAddress(patientDto.getAddress());

        return patientMapper.toDto(
                patientRepository.save(patient)
        );
    }

    public void deleteAll() {
        patientRepository.deleteAll();
    }

    public void delete(Long id) {
        patientRepository.deleteById(id);
    }
}
