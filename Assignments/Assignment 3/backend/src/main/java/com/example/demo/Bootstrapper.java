package com.example.demo;

import com.example.demo.consultation.ConsultationRepository;
import com.example.demo.consultation.ConsultationService;
import com.example.demo.consultation.dto.ConsultationDTO;
import com.example.demo.patient.PatientRepository;
import com.example.demo.patient.PatientService;
import com.example.demo.patient.dto.PatientDTO;
import com.example.demo.patient.mapper.PatientMapper;
import com.example.demo.patient.model.Patient;
import com.example.demo.security.AuthService;
import com.example.demo.security.dto.SignupRequest;
import com.example.demo.user.RoleRepository;
import com.example.demo.user.UserRepository;
import com.example.demo.user.dto.UserListDTO;
import com.example.demo.user.mapper.UserMapper;
import com.example.demo.user.model.ERole;
import com.example.demo.user.model.Role;
import com.example.demo.user.UserService;
import com.example.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    private final UserMapper userMapper;

    private final PatientService patientService;

    private final PatientRepository patientRepository;

    private final PatientMapper patientMapper;

    private final ConsultationService consultationService;

    private final ConsultationRepository consultationRepository;

    private final AuthService authService;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            consultationRepository.deleteAll();
            patientRepository.deleteAll();
            userRepository.deleteAll();
            roleRepository.deleteAll();

            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
            authService.register(SignupRequest.builder()
                    .email("admin@email.com")
                    .username("admin")
                    .password("Adminpass!1")
                    .name("Roxi")
                    .roles(Set.of("ADMIN"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("doctor@email.com")
                    .username("doctor")
                    .password("Doctorpass!1")
                    .name("Roxana")
                    .roles(Set.of("DOCTOR"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("secretary@email.com")
                    .username("secretary")
                    .password("Secretarypass!1")
                    .name("Roxana Ciocian")
                    .roles(Set.of("SECRETARY"))
                    .build());

            UserListDTO user = UserListDTO.builder()
                    .email("doc@yahoo.com")
                    .username("doctor2")
                    .password("Doc2pass!1")
                    .name("Dcotor 2")
                    .roles(Set.of("DOCTOR"))
                    .build();
            userService.create(user);

            UserListDTO user2 = UserListDTO.builder()
                    .email("doc3@yahoo.com")
                    .username("doctor3")
                    .password("Doc3pass!1")
                    .name("Doctor 3")
                    .roles(Set.of("DOCTOR"))
                    .build();
            userService.create(user2);

            User userrr = userRepository.findByUsername("doctor2")
                    .orElseThrow(() -> new EntityNotFoundException("User Not Found with username: "));
            User userrr2 = userRepository.findByUsername("doctor3")
                    .orElseThrow(() -> new EntityNotFoundException("User Not Found with username: "));


            PatientDTO patientDTO = PatientDTO.builder()
                    .firstName("Dorel")
                    .lastName("Pop")
                    .idNumber(123456L)
                    .cnp(1234567891234L)
                    .birthDate(LocalDate.now())
                    .address("Zalau")
                    .build();

            PatientDTO patientDTO1 = PatientDTO.builder()
                    .firstName("Anghel")
                    .lastName("Pop")
                    .idNumber(126456L)
                    .cnp(1234767891234L)
                    .birthDate(LocalDate.now())
                    .address("Cluj")
                    .build();
            patientService.create(patientDTO);
            patientService.create(patientDTO1);
            Patient patient1 = patientRepository.findByCnp(1234567891234L);
            Patient patient2 = patientRepository.findByCnp(1234767891234L);

            ConsultationDTO consultationDTO1 = ConsultationDTO.builder()
                    //.patientId(patientMapper.fromDto(patientDTO))
                    //.doctorId(userMapper.userFromListDTO(user))
                    .patientId(patient1.getId())
                    .doctorId(userrr.getId())
                    .date(LocalDateTime.of(2021, 05, 13, 21, 01))
                    .description("Description 1")
                    .build();
            consultationService.create(consultationDTO1);

            ConsultationDTO consultationDTO2 = ConsultationDTO.builder()
                    //.patientId(patientMapper.fromDto(patientDTO1))
                    //.doctorId(userMapper.userFromListDTO(user2))
                    .patientId(patient2.getId())
                    .doctorId(userrr2.getId())
                    .date(LocalDateTime.of(2021, 05, 13, 13, 01))
                    .description("Description 2")
                    .build();
            consultationService.create(consultationDTO2);

            ConsultationDTO consultationDTO3 = ConsultationDTO.builder()
                    //.patientId(patientMapper.fromDto(patientDTO))
                    //.doctorId(userMapper.userFromListDTO(user2))
                    .patientId(patient1.getId())
                    .doctorId(userrr2.getId())
                    .date(LocalDateTime.of(2021, 05, 13, 13, 04))
                    .description("Description 3")
                    .build();
            consultationService.create(consultationDTO3);

            System.out.println(consultationService.findAllConsultations());
           // System.out.println(userService.findDoctorByUsername("doc23"));
        }
    }
}
