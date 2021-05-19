package com.example.demo.consultation;

import com.example.demo.consultation.model.Consultation;
import com.example.demo.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

//    @Query("SELECT consultation FROM Consultation consultation WHERE consultation.doctorId = ?1 and consultation.date = ?2 ")
//   List<Consultation> availableDoctor(Long id, LocalDateTime date);
    Boolean existsByDoctorIdAndDate(User doctor, LocalDateTime date);
}
