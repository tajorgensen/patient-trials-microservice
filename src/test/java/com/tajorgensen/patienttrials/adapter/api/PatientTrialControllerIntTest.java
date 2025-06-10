package com.tajorgensen.patienttrials.adapter.api;

import com.tajorgensen.patienttrials.adapter.model.PatientTrialWebModel;
import com.tajorgensen.patienttrials.common.exception.ErrorConstants;
import com.tajorgensen.patienttrials.common.exception.GlobalExceptionHandler;
import com.tajorgensen.patienttrials.common.exception.ResourceNotFoundException;
import com.tajorgensen.patienttrials.domain.port.PatientTrialPort;
import com.tajorgensen.patienttrials.domain.usecase.CreatePatientTrialUseCase;
import com.tajorgensen.patienttrials.domain.usecase.DeletePatientTrialUseCase;
import com.tajorgensen.patienttrials.domain.usecase.GetPatientTrialUseCase;
import com.tajorgensen.patienttrials.domain.usecase.UpdatePatientTrialUseCase;
import com.tajorgensen.patienttrials.test.IntegrationTest;
import com.tajorgensen.patienttrials.utils.PatientTrialTestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatientTrialController.class)
@ContextConfiguration(classes = {ObjectMapper.class, CreatePatientTrialUseCase.class, GetPatientTrialUseCase.class, DeletePatientTrialUseCase.class, UpdatePatientTrialUseCase.class, PatientTrialController.class, GlobalExceptionHandler.class})
@IntegrationTest
class PatientTrialControllerIntTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientTrialPort port;

    @BeforeEach
    void setup() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testCreatePatientTrialWithValidationError() throws Exception {
        PatientTrialWebModel webModel = PatientTrialTestUtils.createWebModel();

        mockMvc.perform(post("/patientTrials").content(objectMapper.writeValueAsString(webModel)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(ErrorConstants.PatientTrialErrorCode.INVALID_CREATE_REQUEST.getCode())));
    }

    @Test
    void testCreatePatientTrial() throws Exception {
        PatientTrialWebModel webModel = PatientTrialTestUtils.createWebModel();
        webModel.setId(null);

        PatientTrialWebModel webModelWithId = PatientTrialTestUtils.createWebModel();
        when(port.createPatientTrial(any())).thenReturn(PatientTrialTestUtils.createDomain());

        mockMvc.perform(post("/patientTrials").content(objectMapper.writeValueAsString(webModel)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(webModelWithId)));
    }

    @Test
    void testUpdatePatientTrialWithValidationErrorIdsDoNotMatch() throws Exception {
        PatientTrialWebModel webModel = PatientTrialTestUtils.createWebModel();

        mockMvc.perform(post("/patientTrials/1").content(objectMapper.writeValueAsString(webModel)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(ErrorConstants.PatientTrialErrorCode.INVALID_UPDATE_REQUEST.getCode())));
    }

    @Test
    void testUpdatePatientTrialWithValidationErrorIdInBodyIsNull() throws Exception {
        PatientTrialWebModel webModel = PatientTrialTestUtils.createWebModel();
        webModel.setId(null);
        mockMvc.perform(post("/patientTrials/1").content(objectMapper.writeValueAsString(webModel)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(ErrorConstants.PatientTrialErrorCode.INVALID_UPDATE_REQUEST.getCode())));
    }

    @Test
    void testUpdatePatientTrial() throws Exception {
        PatientTrialWebModel webModel = PatientTrialTestUtils.createWebModel();
        when(port.updatePatientTrial(any())).thenReturn(PatientTrialTestUtils.createDomain());

        mockMvc.perform(post("/patientTrials/" + webModel.getId().toString()).content(objectMapper.writeValueAsString(webModel)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(webModel)));
    }

    @Test
    void testGetPatientTrialDoesNotExist() throws Exception {
        when(port.getPatientTrialById(eq(1L))).thenThrow(new ResourceNotFoundException(ErrorConstants.PatientTrialErrorCode.GET_ID_NOT_FOUND.getCode(), "Test"));

        mockMvc.perform(get("/patientTrials/1"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(StringUtils.EMPTY));
    }

    @Test
    void testGetPatientTrial() throws Exception {
        PatientTrialWebModel webModel = PatientTrialTestUtils.createWebModel();
        when(port.getPatientTrialById(eq(webModel.getId()))).thenReturn(PatientTrialTestUtils.createDomain());

        mockMvc.perform(get("/patientTrials/" + webModel.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(webModel)));
    }

    @Test
    void testDeletePatientTrialDoesNotExist() throws Exception {
        doThrow(new ResourceNotFoundException(ErrorConstants.PatientTrialErrorCode.DELETE_ID_NOT_FOUND.getCode(), "Test")).when(port).deletePatientTrialById(eq(1L));

        mockMvc.perform(delete("/patientTrials/1"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(StringUtils.EMPTY));
    }

    @Test
    void testDeletePatientTrial() throws Exception {
        PatientTrialWebModel webModel = PatientTrialTestUtils.createWebModel();
        when(port.getPatientTrialById(eq(webModel.getId()))).thenReturn(PatientTrialTestUtils.createDomain());

        mockMvc.perform(get("/patientTrials/" + webModel.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(webModel)));
    }

}