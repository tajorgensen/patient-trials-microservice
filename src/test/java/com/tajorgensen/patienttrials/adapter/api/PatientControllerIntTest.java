package com.tajorgensen.patienttrials.adapter.api;

import com.tajorgensen.patienttrials.adapter.model.PatientWebModel;
import com.tajorgensen.patienttrials.common.exception.ErrorConstants;
import com.tajorgensen.patienttrials.common.exception.GlobalExceptionHandler;
import com.tajorgensen.patienttrials.common.exception.ResourceNotFoundException;
import com.tajorgensen.patienttrials.domain.port.PatientPort;
import com.tajorgensen.patienttrials.domain.usecase.CreatePatientUseCase;
import com.tajorgensen.patienttrials.domain.usecase.DeletePatientUseCase;
import com.tajorgensen.patienttrials.domain.usecase.GetPatientUseCase;
import com.tajorgensen.patienttrials.domain.usecase.UpdatePatientUseCase;
import com.tajorgensen.patienttrials.test.IntegrationTest;
import com.tajorgensen.patienttrials.utils.PatientTestUtils;
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

@WebMvcTest(PatientController.class)
@ContextConfiguration(classes = {ObjectMapper.class, CreatePatientUseCase.class, GetPatientUseCase.class, DeletePatientUseCase.class, UpdatePatientUseCase.class, PatientController.class, GlobalExceptionHandler.class})
@IntegrationTest
class PatientControllerIntTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientPort port;

    @BeforeEach
    void setup() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testCreatePatientWithValidationError() throws Exception {
        PatientWebModel webModel = PatientTestUtils.createWebModel();

        mockMvc.perform(post("/patients").content(objectMapper.writeValueAsString(webModel)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(ErrorConstants.PatientErrorCode.INVALID_CREATE_REQUEST.getCode())));
    }

    @Test
    void testCreatePatient() throws Exception {
        PatientWebModel webModel = PatientTestUtils.createWebModel();
        webModel.setId(null);

        PatientWebModel webModelWithId = PatientTestUtils.createWebModel();
        when(port.createPatient(any())).thenReturn(PatientTestUtils.createDomain());

        mockMvc.perform(post("/patients").content(objectMapper.writeValueAsString(webModel)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(webModelWithId)));
    }

    @Test
    void testUpdatePatientWithValidationErrorIdsDoNotMatch() throws Exception {
        PatientWebModel webModel = PatientTestUtils.createWebModel();

        mockMvc.perform(post("/patients/1").content(objectMapper.writeValueAsString(webModel)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(ErrorConstants.PatientErrorCode.INVALID_UPDATE_REQUEST.getCode())));
    }

    @Test
    void testUpdatePatientWithValidationErrorIdInBodyIsNull() throws Exception {
        PatientWebModel webModel = PatientTestUtils.createWebModel();
        webModel.setId(null);
        mockMvc.perform(post("/patients/1").content(objectMapper.writeValueAsString(webModel)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(ErrorConstants.PatientErrorCode.INVALID_UPDATE_REQUEST.getCode())));
    }

    @Test
    void testUpdatePatient() throws Exception {
        PatientWebModel webModel = PatientTestUtils.createWebModel();
        when(port.updatePatient(any())).thenReturn(PatientTestUtils.createDomain());

        mockMvc.perform(post("/patients/" + webModel.getId().toString()).content(objectMapper.writeValueAsString(webModel)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(webModel)));
    }

    @Test
    void testGetPatientDoesNotExist() throws Exception {
        when(port.getPatientById(eq(1L))).thenThrow(new ResourceNotFoundException(ErrorConstants.PatientErrorCode.GET_ID_NOT_FOUND.getCode(), "Test"));

        mockMvc.perform(get("/patients/1"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(StringUtils.EMPTY));
    }

    @Test
    void testGetPatient() throws Exception {
        PatientWebModel webModel = PatientTestUtils.createWebModel();
        when(port.getPatientById(eq(webModel.getId()))).thenReturn(PatientTestUtils.createDomain());

        mockMvc.perform(get("/patients/" + webModel.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(webModel)));
    }

    @Test
    void testDeletePatientDoesNotExist() throws Exception {
        doThrow(new ResourceNotFoundException(ErrorConstants.PatientErrorCode.DELETE_ID_NOT_FOUND.getCode(), "Test")).when(port).deletePatientById(eq(1L));

        mockMvc.perform(delete("/patients/1"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(StringUtils.EMPTY));
    }

    @Test
    void testDeletePatient() throws Exception {
        PatientWebModel webModel = PatientTestUtils.createWebModel();
        when(port.getPatientById(eq(webModel.getId()))).thenReturn(PatientTestUtils.createDomain());

        mockMvc.perform(get("/patients/" + webModel.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(webModel)));
    }

}