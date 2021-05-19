package com.example.demo.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor

public class NewConsultationMessage {
    private final Long patientId;
    private final LocalDateTime date;
    private final Long doctorId;

//    public String sendMessage(){
//        return "There is a new consultation on " + date + " with patient having id "
//                + patientId + " for doctor with id " + doctorId;
//    }
}
