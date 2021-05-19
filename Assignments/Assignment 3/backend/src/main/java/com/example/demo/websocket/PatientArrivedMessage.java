package com.example.demo.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PatientArrivedMessage {
    private final Long patientId;
    private final LocalDateTime date;

//    public String sendMessage(){
//        return "Patient with id " + patientId + " arrived for consultation on date " + date.toString() ;
//    }
}
