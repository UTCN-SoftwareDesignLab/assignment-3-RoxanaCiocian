package com.example.demo.patient;

import com.example.demo.patient.dto.PatientDTO;
import com.example.demo.patient.model.Patient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
public class PatientServiceIntegrationTest {
    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    @BeforeEach
    void setUp() {
        patientRepository.deleteAll();
    }

    @Test
    void findAll() {
        int nrPatients = 10;
        List<Patient> patients = new ArrayList<>();
        for (int i = 0; i < nrPatients; i++) {
            Patient patient = Patient.builder()
                    .firstName("Name " + i)
                    .address("Address" + i)
                    .idNumber((long)i)
                    .cnp((long)i)
                    .build();
            patients.add(patient);
            patientRepository.save(patient);
        }

        List<PatientDTO> patientDTO = patientService.findAllPatients();

        for (int i = 0; i < nrPatients; i++) {
            assertEquals(patients.get(i).getId(), patientDTO.get(i).getId());
            assertEquals(patients.get(i).getFirstName(), patientDTO.get(i).getFirstName());
        }
    }

    @Test
    void create() {
        int nrPatients = 10;
        List<Patient> patients = new ArrayList<>();
        for (int i = 0; i < nrPatients; i++) {
            Patient patient = Patient.builder()
                    .firstName("Name " + i)
                    .address("Address" + i)
                    .idNumber((long)i)
                    .cnp((long)i)
                    .build();
            patients.add(patient);
            patientRepository.save(patient);
        }

        List<PatientDTO> patientDTO = patientService.findAllPatients();

        for (int i = 0; i < nrPatients; i++) {
            assertEquals(patients.get(i).getId(), patientDTO.get(i).getId());
            assertEquals(patients.get(i).getFirstName(), patientDTO.get(i).getFirstName());
        }
    }

    @Test
    void update() {
        int nrPatients = 10;
        List<Patient> patients = new ArrayList<>();
        for (int i = 0; i < nrPatients; i++) {
            Patient patient = Patient.builder()
                    .firstName("Name " + i)
                    .address("Address" + i)
                    .idNumber((long)i)
                    .cnp((long)i)
                    .build();
            patients.add(patient);

            patient.setFirstName("neeew name" + i);
            patientRepository.save(patient);
        }

        List<PatientDTO> patientDTO = patientService.findAllPatients();

        for (int i = 0; i < nrPatients; i++) {
            assertEquals(patients.get(i).getId(), patientDTO.get(i).getId());
            assertEquals(patients.get(i).getFirstName(), patientDTO.get(i).getFirstName());
        }
    }

    @Test
    void deleteAll() {
        patientRepository.deleteAll();

        List<PatientDTO> all = patientService.findAllPatients();

        Assertions.assertEquals(0, all.size());
    }
}
