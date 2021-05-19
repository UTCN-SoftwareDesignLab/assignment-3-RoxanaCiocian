package com.example.demo.consultation;

import com.example.demo.BaseControllerTest;
import com.example.demo.TestCreationFactory;
import com.example.demo.consultation.controller.ConsultationController;

import com.example.demo.consultation.dto.ConsultationDTO;
import com.example.demo.patient.controller.PatientController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static com.example.demo.UrlMapping.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;

import static org.mockito.Mockito.when;

public class ConsultationControllerTest extends BaseControllerTest {
    @InjectMocks
    private ConsultationController consultationController;

    @Mock
    private ConsultationService consultationService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        consultationController = new ConsultationController(consultationService);
        mockMvc = MockMvcBuilders.standaloneSetup(consultationController).build();
    }

    @Test
    void allConsultations() throws Exception{
        List<ConsultationDTO> consultationDTOList = TestCreationFactory.listOf(ConsultationDTO.class);
        when(consultationService.findAllConsultations()).thenReturn(consultationDTOList);

        ResultActions resultActions = mockMvc.perform(get(CONSULTATIONS));
        resultActions.andExpect(status().isOk())
                .andExpect(jsonContentToBe(consultationDTOList));
    }

    @Test
    void createConsultation() throws Exception{
        ConsultationDTO consultationDTO = (ConsultationDTO) TestCreationFactory.listOf(ConsultationDTO.class).get(0);
        //when(consultationService.create(consultationDTO)).thenReturn(consultationDTO);

        ResultActions result = performPostWithRequestBody(CONSULTATIONS, consultationDTO);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(consultationDTO));
    }

    @Test
    void editConsultation() throws Exception{
        ConsultationDTO consultationDTO = (ConsultationDTO) TestCreationFactory.listOf(ConsultationDTO.class).get(0);
        when(consultationService.update(consultationDTO.getId(), consultationDTO)).thenReturn(consultationDTO);

        ResultActions result = performPutWithRequestBodyAndPathVariable(CONSULTATIONS + ENTITY, consultationDTO, consultationDTO.getId());
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(consultationDTO));
    }

    @Test
    void deleteConsultation() throws Exception{
        long id = 1L;
        doNothing().when(consultationService).delete(id);

        ResultActions result = performDeleteWithPathVariable(CONSULTATIONS + ENTITY, id);
        result.andExpect(status().isOk());
    }

    @Test
    void deleteAll() throws Exception {
        doNothing().when(consultationService).deleteAll();

        ResultActions result = performDeleteAll(CONSULTATIONS);

        result.andExpect(status().isOk());
        verify(consultationService, times(1)).deleteAll();
    }
}
