package com.example.demo.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {
    @MessageMapping("/notification")
    @SendTo("/doctor/newConsultNotification")
    public OutputMessage newConsult(NewConsultationMessage newConsultationMessage){
        return new OutputMessage("There is a new consultation on " + newConsultationMessage.getDate() + " with patient having id "
                + newConsultationMessage.getPatientId() + " for doctor with id " + newConsultationMessage.getDoctorId()
        );
    }
}
