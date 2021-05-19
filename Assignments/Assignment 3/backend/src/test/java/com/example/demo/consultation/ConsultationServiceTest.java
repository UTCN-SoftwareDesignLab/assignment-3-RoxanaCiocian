package com.example.demo.consultation;

import com.example.demo.TestCreationFactory;
import com.example.demo.consultation.dto.ConsultationDTO;
import com.example.demo.consultation.mapper.ConsultationMapper;
import com.example.demo.consultation.model.Consultation;
import com.example.demo.patient.PatientService;
import com.example.demo.patient.model.Patient;
import com.example.demo.user.UserService;
import com.example.demo.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class ConsultationServiceTest {
    @InjectMocks
    private ConsultationService consultationService;

    @Mock
    private ConsultationRepository consultationRepository;

    @Mock
    private ConsultationMapper consultationMapper;

    @Mock
    private UserService userService;

    @Mock
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        //consultationService = new ConsultationService(userService, patientService, consultationRepository, consultationMapper);
    }

    @Test
    void findAll() {
        List<Consultation> consultations = TestCreationFactory.listOf(Consultation.class);
        when(consultationRepository.findAll()).thenReturn(consultations);

        List<ConsultationDTO> all = consultationService.findAllConsultations();
        Assertions.assertEquals(all.size(), consultations.size());

    }

    @Test
    void createConsultation() {
        User doc = new User();
        Patient patient = new Patient();

        Consultation consultation = Consultation.builder()
                .date(LocalDateTime.now())
                .description("aaaa")
                .build();
        ConsultationDTO consultationDTO = ConsultationDTO.builder()
                .date(LocalDateTime.now())
                .description("aaa")
                .build();

        when(consultationMapper.fromDto(consultationDTO)).thenReturn(consultation);
        when(consultationMapper.toDto(consultation)).thenReturn(consultationDTO);
        when(consultationRepository.save(consultation)).thenReturn(consultation);

        //Assertions.assertEquals(consultation.getDescription(), consultationService.create(consultationDTO));
    }

    @Test
    void updateConsultation() {
        User doc = new User();
        Patient patient = new Patient();

        Consultation consultation = Consultation.builder()
                .date(LocalDateTime.now())
                .description("aaaa")
                .build();
        ConsultationDTO consultationDTO = ConsultationDTO.builder()
                .date(LocalDateTime.now())
                .description("newAaaa")
                .build();
        when(consultationRepository.findById(consultation.getId())).thenReturn(Optional.of(consultation));
        consultationService.update(consultation.getId(), consultationDTO);
        Assertions.assertEquals("newAaaa", consultationDTO.getDescription());

    }

    @Test
    void delete(){
        User doc = new User();
        Patient patient = new Patient();

        Consultation consultation = Consultation.builder()
                .date(LocalDateTime.now())
                .description("aaaa")
                .doctorId(doc)
                .patientId(patient)
                .build();

        when(consultationRepository.save(consultation)).thenReturn(consultation);
        consultationRepository.delete(consultation);
        List<ConsultationDTO> all = consultationService.findAllConsultations();
        Assertions.assertEquals(all.size(), 0);
    }

}
