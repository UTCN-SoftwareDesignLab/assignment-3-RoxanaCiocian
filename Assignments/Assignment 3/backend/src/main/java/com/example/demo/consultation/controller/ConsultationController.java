package com.example.demo.consultation.controller;

import com.example.demo.consultation.ConsultationService;
import com.example.demo.consultation.dto.ConsultationDTO;
import com.example.demo.user.dto.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.UrlMapping.*;

@RestController
@RequestMapping(CONSULTATIONS)
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService consultationService;

    @GetMapping
    public List<ConsultationDTO> allConsultations() {
        return consultationService.findAllConsultations();
    }

    @PostMapping
    public void createConsultation(@RequestBody ConsultationDTO consultationDTO){
        consultationService.create(consultationDTO);
        //consultationService.patientCreateConsultation(consultationDTO);
    }

    @PutMapping(ENTITY)
    public ConsultationDTO updateConsultation(@PathVariable Long id, @RequestBody ConsultationDTO consultationDTO){
        consultationService.patientArrived(consultationDTO);
        return consultationService.update(id, consultationDTO);

    }
    @PostMapping("/doctor/patientArrived")
    public void patientArrived(ConsultationDTO consultationDTO){
        consultationService.patientArrived(consultationDTO);
    }

    @PutMapping(ENTITY + DESCRIPTION)
    public ConsultationDTO updateDescription(@PathVariable Long id, @RequestBody ConsultationDTO consultationDTO){
        return consultationService.updateDescription(id, consultationDTO);
    }

    @DeleteMapping(ENTITY)
    public void deleteConsultation(@PathVariable Long id){
        consultationService.delete(id);
    }



}
