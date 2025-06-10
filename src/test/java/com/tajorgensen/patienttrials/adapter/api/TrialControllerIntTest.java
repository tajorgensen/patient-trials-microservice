package com.tajorgensen.patienttrials.adapter.api;

import com.tajorgensen.patienttrials.adapter.model.TrialAdverseEventListWebModel;
import com.tajorgensen.patienttrials.adapter.model.TrialDrugAverageDosageListWebModel;
import com.tajorgensen.patienttrials.adapter.model.TrialListWebModel;
import com.tajorgensen.patienttrials.adapter.model.TrialPatientListWebModel;
import com.tajorgensen.patienttrials.adapter.model.TrialWebModel;
import com.tajorgensen.patienttrials.common.exception.ErrorConstants;
import com.tajorgensen.patienttrials.common.exception.GlobalExceptionHandler;
import com.tajorgensen.patienttrials.common.exception.ResourceNotFoundException;
import com.tajorgensen.patienttrials.domain.port.AdverseEventPort;
import com.tajorgensen.patienttrials.domain.port.GetAverageDosagePort;
import com.tajorgensen.patienttrials.domain.port.PatientTrialPort;
import com.tajorgensen.patienttrials.domain.port.TrialPort;
import com.tajorgensen.patienttrials.domain.usecase.CreateTrialUseCase;
import com.tajorgensen.patienttrials.domain.usecase.DeleteTrialUseCase;
import com.tajorgensen.patienttrials.domain.usecase.GetAdverseEventsForTrialUseCase;
import com.tajorgensen.patienttrials.domain.usecase.GetAllTrialsUseCase;
import com.tajorgensen.patienttrials.domain.usecase.GetAverageDrugDosageForTrialUseCase;
import com.tajorgensen.patienttrials.domain.usecase.GetPatientsForTrialUseCase;
import com.tajorgensen.patienttrials.domain.usecase.GetTrialUseCase;
import com.tajorgensen.patienttrials.domain.usecase.UpdateTrialUseCase;
import com.tajorgensen.patienttrials.test.IntegrationTest;
import com.tajorgensen.patienttrials.utils.AdverseEventTestUtils;
import com.tajorgensen.patienttrials.utils.AverageDrugDosageResultTestUtils;
import com.tajorgensen.patienttrials.utils.PatientTrialStatusTestUtils;
import com.tajorgensen.patienttrials.utils.PatientTrialTestUtils;
import com.tajorgensen.patienttrials.utils.TrialDrugAverageDosageTestUtils;
import com.tajorgensen.patienttrials.utils.TrialTestUtils;
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

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TrialController.class)
@ContextConfiguration(classes = {
        ObjectMapper.class,
        CreateTrialUseCase.class,
        GetTrialUseCase.class,
        DeleteTrialUseCase.class,
        UpdateTrialUseCase.class,
        GetAllTrialsUseCase.class,
        GetPatientsForTrialUseCase.class,
        GetAverageDrugDosageForTrialUseCase.class,
        GetAdverseEventsForTrialUseCase.class,
        TrialController.class,
        GlobalExceptionHandler.class})
@IntegrationTest
class TrialControllerIntTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrialPort trialPort;

    @MockBean
    private AdverseEventPort adverseEventPort;

    @MockBean
    private PatientTrialPort patientTrialPort;

    @MockBean
    private GetAverageDosagePort getAverageDosagePort;

    @BeforeEach
    void setup() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testCreateTrialWithValidationError() throws Exception {
        TrialWebModel webModel = TrialTestUtils.createWebModel();

        mockMvc.perform(post("/trials").content(objectMapper.writeValueAsString(webModel)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(ErrorConstants.TrialErrorCode.INVALID_CREATE_REQUEST.getCode())));
    }

    @Test
    void testCreateTrial() throws Exception {
        TrialWebModel webModel = TrialTestUtils.createWebModel();
        webModel.setId(null);

        TrialWebModel webModelWithId = TrialTestUtils.createWebModel();
        when(trialPort.createTrial(any())).thenReturn(TrialTestUtils.createDomain());

        mockMvc.perform(post("/trials").content(objectMapper.writeValueAsString(webModel)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(webModelWithId)));
    }

    @Test
    void testUpdateTrialWithValidationErrorIdsDoNotMatch() throws Exception {
        TrialWebModel webModel = TrialTestUtils.createWebModel();

        mockMvc.perform(post("/trials/1").content(objectMapper.writeValueAsString(webModel)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(ErrorConstants.TrialErrorCode.INVALID_UPDATE_REQUEST.getCode())));
    }

    @Test
    void testUpdateTrialWithValidationErrorIdInBodyIsNull() throws Exception {
        TrialWebModel webModel = TrialTestUtils.createWebModel();
        webModel.setId(null);
        mockMvc.perform(post("/trials/1").content(objectMapper.writeValueAsString(webModel)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(ErrorConstants.TrialErrorCode.INVALID_UPDATE_REQUEST.getCode())));
    }

    @Test
    void testUpdateTrial() throws Exception {
        TrialWebModel webModel = TrialTestUtils.createWebModel();
        when(trialPort.updateTrial(any())).thenReturn(TrialTestUtils.createDomain());

        mockMvc.perform(post("/trials/" + webModel.getId().toString()).content(objectMapper.writeValueAsString(webModel)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(webModel)));
    }

    @Test
    void testGetTrialDoesNotExist() throws Exception {
        when(trialPort.getTrialById(eq(1L))).thenThrow(new ResourceNotFoundException(ErrorConstants.TrialErrorCode.GET_ID_NOT_FOUND.getCode(), "Test"));

        mockMvc.perform(get("/trials/1"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(StringUtils.EMPTY));
    }

    @Test
    void testGetTrial() throws Exception {
        TrialWebModel webModel = TrialTestUtils.createWebModel();
        when(trialPort.getTrialById(eq(webModel.getId()))).thenReturn(TrialTestUtils.createDomain());

        mockMvc.perform(get("/trials/" + webModel.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(webModel)));
    }

    @Test
    void testDeleteTrialDoesNotExist() throws Exception {
        doThrow(new ResourceNotFoundException(ErrorConstants.TrialErrorCode.DELETE_ID_NOT_FOUND.getCode(), "Test")).when(trialPort).deleteTrialById(eq(1L));

        mockMvc.perform(delete("/trials/1"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(StringUtils.EMPTY));
    }

    @Test
    void testDeleteTrial() throws Exception {
        TrialWebModel webModel = TrialTestUtils.createWebModel();
        when(trialPort.getTrialById(eq(webModel.getId()))).thenReturn(TrialTestUtils.createDomain());

        mockMvc.perform(get("/trials/" + webModel.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(webModel)));
    }

    @Test
    void testGetAllTrials() throws Exception {
        TrialWebModel webModel = TrialTestUtils.createWebModel();
        when(trialPort.getAllTrials()).thenReturn(List.of(TrialTestUtils.createDomain()));

        mockMvc.perform(get("/trials"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(TrialListWebModel.builder().trials(List.of(webModel)).build())));
    }

    @Test
    void testGetAllTrials_NoneExist() throws Exception {
        when(trialPort.getAllTrials()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/trials"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(TrialListWebModel.builder().trials(Collections.emptyList()).build())));
    }

    @Test
    void testGetAllPatientsByTrialId() throws Exception {
        TrialWebModel webModel = TrialTestUtils.createWebModel();

        when(patientTrialPort.getAllPatientsByTrialId(eq(webModel.getId()))).thenReturn(List.of(PatientTrialTestUtils.createEagerDomain()));

        mockMvc.perform(get("/trials/" + webModel.getId() + "/patients"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(TrialPatientListWebModel.builder().patients(List.of(PatientTrialStatusTestUtils.createWebModel())).build())));

    }

    @Test
    void testGetAllPatientsByTrialId_NoneExist() throws Exception {
        when(patientTrialPort.getAllPatientsByTrialId(anyLong())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/trials/1/patients"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(TrialPatientListWebModel.builder().patients(Collections.emptyList()).build())));
    }

    @Test
    void testGetAllAdverseEventsByTrialId() throws Exception {
        TrialWebModel webModel = TrialTestUtils.createWebModel();
        when(adverseEventPort.getAdverseEventsByTrialId(eq(webModel.getId()))).thenReturn(List.of(AdverseEventTestUtils.createDomain()));

        mockMvc.perform(get("/trials/" + webModel.getId() + "/patients/adverseEvents"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(TrialAdverseEventListWebModel.builder().adverseEvents(List.of(AdverseEventTestUtils.createWebModel())).build())));
    }

    @Test
    void testGetAllAdverseEventsByTrialId_NoneExist() throws Exception {
        when(adverseEventPort.getAdverseEventsByTrialId(anyLong())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/trials/1/patients/adverseEvents"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(TrialAdverseEventListWebModel.builder().adverseEvents(Collections.emptyList()).build())));
    }

    @Test
    void testGetAverageDosageForTrial() throws Exception {
        TrialWebModel webModel = TrialTestUtils.createWebModel();
        when(getAverageDosagePort.getAverageDosageForTrialId(eq(webModel.getId()))).thenReturn(List.of(AverageDrugDosageResultTestUtils.createDomain()));

        mockMvc.perform(get("/trials/" + webModel.getId() + "/averageDosage"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(TrialDrugAverageDosageListWebModel.builder().trialDrugs(List.of(TrialDrugAverageDosageTestUtils.createWebModel())).build())));
    }

    @Test
    void testGetAverageDosageForTrial_NoTrialExists() throws Exception {
        when(getAverageDosagePort.getAverageDosageForTrialId(anyLong())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/trials/1/averageDosage"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(TrialDrugAverageDosageListWebModel.builder().trialDrugs(Collections.emptyList()).build())));
    }

}