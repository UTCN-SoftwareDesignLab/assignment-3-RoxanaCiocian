package com.example.demo.patient;
import com.example.demo.TestCreationFactory;
import com.example.demo.patient.mapper.PatientMapper;
import com.example.demo.patient.model.Patient;
import com.example.demo.patient.dto.PatientDTO;
import com.example.demo.patient.PatientRepository;
import com.example.demo.patient.PatientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class PatientServiceTest {
    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper patientMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patientService = new PatientService(patientRepository, patientMapper);
    }

    @Test
    void findAll() {
        List<Patient> patients = TestCreationFactory.listOf(Patient.class);
        when(patientRepository.findAll()).thenReturn(patients);

        List<PatientDTO> all = patientService.findAllPatients();
        Assertions.assertEquals(all.size(), patients.size());
    }

    @Test
    void createPatient() {

        Patient patient1 = Patient.builder()
                .firstName("Patient")
                .lastName("lastt")
                .address("addddrrress")
                .build();

        PatientDTO patient2 = PatientDTO.builder()
                .firstName("Patient")
                .lastName("lastt")
                .address("addddrrress")
                .build();

        when(patientMapper.fromDto(patient2)).thenReturn(patient1);
        when(patientMapper.toDto(patient1)).thenReturn(patient2);
        when(patientRepository.save(patient1)).thenReturn(patient1);

        Assertions.assertEquals(patient1.getAddress(), patientService.create(patient2).getAddress());
    }

    @Test
    void editPatient() {
        Patient patient1 = Patient.builder()
                .firstName("Patient")
                .lastName("lastt")
                .address("addddrrress")
                .build();

        PatientDTO patient2 = PatientDTO.builder()
                .firstName("Patient2")
                .lastName("lastt2")
                .address("addddrrress2")
                .build();

        when(patientRepository.findById(patient1.getId())).thenReturn(Optional.of(patient1));

        patientService.update(patient1.getId(), patient2);
        Assertions.assertEquals("Patient2", patient2.getFirstName());
    }

    @Test
    void delete() {
        Patient patient1 = Patient.builder()
                .firstName("Patient")
                .lastName("lastt")
                .address("addddrrress")
                .build();

        when(patientRepository.save(patient1)).thenReturn(patient1);

        patientRepository.delete(patient1);

        List<PatientDTO> all = patientService.findAllPatients();
        Assertions.assertEquals(all.size(), 0);
    }
}
