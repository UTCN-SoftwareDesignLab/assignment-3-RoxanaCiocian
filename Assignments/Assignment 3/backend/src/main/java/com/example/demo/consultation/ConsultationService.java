package com.example.demo.consultation;

import com.example.demo.consultation.dto.ConsultationDTO;
import com.example.demo.consultation.mapper.ConsultationMapper;
import com.example.demo.consultation.model.Consultation;
import com.example.demo.patient.PatientService;
import com.example.demo.patient.model.Patient;
import com.example.demo.user.UserService;
import com.example.demo.user.model.User;
import com.example.demo.websocket.OutputMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ConsultationService {
    private final UserService userService;

    private final PatientService patientService;

    private final ConsultationRepository consultationRepository;

    private final ConsultationMapper consultationMapper;

    private  final SimpMessageSendingOperations messagingTemplate;


    private Consultation findById(Long id) {
        return consultationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Consultation not found: " + id));
    }

    public List<ConsultationDTO> findAllConsultations() {

        return consultationRepository.findAll().stream().
                map(consultationMapper::toDto)
                .collect(Collectors.toList());
    }


    public Boolean availableConsultationAtHour(ConsultationDTO consultationDTO){
        LocalDateTime consultationHour = consultationDTO.getDate();
        User doctor = userService.findDoctorById(consultationDTO.getDoctorId());

        if(consultationRepository.existsByDoctorIdAndDate(doctor, consultationHour ))
            return false;

        return !consultationRepository.existsByDoctorIdAndDate(doctor, consultationHour.minusHours(1)) && !consultationRepository.existsByDoctorIdAndDate(doctor, consultationHour.plusHours(1));
    }

    public void create(ConsultationDTO consultationDTO) {
        Patient patient = patientService.findById(consultationDTO.getPatientId());
        User doctor = userService.findDoctorById(consultationDTO.getDoctorId());

//        String datee = consultationDTO.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH"));
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
//        LocalDateTime dateTime = LocalDateTime.parse(datee, formatter);

        consultationDTO.setDate(consultationDTO.getDate().truncatedTo(ChronoUnit.HOURS));
        if(availableConsultationAtHour(consultationDTO)){
            consultationRepository.save(Consultation.builder()
                    .id(consultationDTO.getId())
                    //.date(dateTime)
                    .date(consultationDTO.getDate())
                    .doctorId(doctor)
                    .patientId(patient)
                    .description(consultationDTO.getDescription())
                    .build());

            patientCreateConsultation(consultationDTO);
        }
    }

    public ConsultationDTO update(Long id, ConsultationDTO consultationDTO){
        Consultation consultation = findById(id);
        User doctor = userService.findDoctorById(consultationDTO.getDoctorId());
        consultation.setDoctorId(doctor);
        consultation.setDate(consultationDTO.getDate());

        return consultationMapper.toDto(consultationRepository.save(consultation));
    }

    public void deleteAll(){
        consultationRepository.deleteAll();
    }

    public void delete(Long id){
        consultationRepository.deleteById(id);
    }

    public ConsultationDTO updateDescription(Long id, ConsultationDTO consultationDTO){
        Consultation consultation = findById(id);

        consultation.setDescription(consultationDTO.getDescription());

        return consultationMapper.toDto(consultationRepository.save(consultation));
    }

    public void patientCreateConsultation(ConsultationDTO consultationDTO) {
        messagingTemplate.convertAndSend("/doctor/newConsultNotification", new OutputMessage("There is a new consultation on " + consultationDTO.getDate() + " with patient having id "
                   + consultationDTO.getPatientId() + " for doctor with id " + consultationDTO.getDoctorId()));
    }

    public void patientArrived(ConsultationDTO consultationDTO){
        messagingTemplate.convertAndSend("/doctor/patientArrived", new OutputMessage("Patient with id " + consultationDTO.getPatientId() +
                " arrived for consultation on date " + consultationDTO.getDate()));

    }

}
