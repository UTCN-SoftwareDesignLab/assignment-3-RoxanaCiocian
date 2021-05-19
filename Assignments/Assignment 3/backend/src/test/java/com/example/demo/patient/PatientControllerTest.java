package com.example.demo.patient;

import com.example.demo.BaseControllerTest;
import com.example.demo.TestCreationFactory;
import com.example.demo.patient.controller.PatientController;
import com.example.demo.patient.dto.PatientDTO;
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

public class PatientControllerTest extends BaseControllerTest {
    @InjectMocks
    private PatientController patientController;

    @Mock
    private PatientService patientService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        patientController = new PatientController(patientService);
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
    }

    @Test
    void allPatients() throws Exception {
        List<PatientDTO> patientDTOList = TestCreationFactory.listOf(PatientDTO.class);
        when(patientService.findAllPatients()).thenReturn(patientDTOList);

        ResultActions result = mockMvc.perform(get(PATIENTS));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(patientDTOList));

    }

    @Test
    void create() throws Exception {
        PatientDTO patient = (PatientDTO) TestCreationFactory.listOf(PatientDTO.class).get(0);
        when(patientService.create(patient)).thenReturn(patient);

        ResultActions result = performPostWithRequestBody(PATIENTS, patient);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(patient));
    }

    @Test
    void edit() throws Exception {
        PatientDTO patient = (PatientDTO) TestCreationFactory.listOf(PatientDTO.class).get(0);
        when(patientService.update(patient.getId(), patient)).thenReturn(patient);

        ResultActions result = performPutWithRequestBodyAndPathVariable(PATIENTS + ENTITY, patient, patient.getId());
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(patient));
    }

    @Test
    void delete() throws Exception {
        long id = 1L;
        doNothing().when(patientService).delete(id);

        ResultActions result = performDeleteWithPathVariable(PATIENTS + ENTITY, id);
        result.andExpect(status().isOk());
    }

    @Test
    void deleteAll() throws Exception {
        doNothing().when(patientService).deleteAll();

        ResultActions result = performDeleteAll(PATIENTS);

        result.andExpect(status().isOk());
        verify(patientService, times(1)).deleteAll();
    }
}
