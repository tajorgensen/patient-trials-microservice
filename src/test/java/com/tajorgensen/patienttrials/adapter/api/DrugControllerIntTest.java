package com.tajorgensen.patienttrials.adapter.api;

import com.tajorgensen.patienttrials.adapter.model.DrugWebModel;
import com.tajorgensen.patienttrials.common.exception.ErrorConstants;
import com.tajorgensen.patienttrials.common.exception.GlobalExceptionHandler;
import com.tajorgensen.patienttrials.common.exception.ResourceNotFoundException;
import com.tajorgensen.patienttrials.domain.port.DrugPort;
import com.tajorgensen.patienttrials.domain.usecase.CreateDrugUseCase;
import com.tajorgensen.patienttrials.domain.usecase.DeleteDrugUseCase;
import com.tajorgensen.patienttrials.domain.usecase.GetDrugUseCase;
import com.tajorgensen.patienttrials.domain.usecase.UpdateDrugUseCase;
import com.tajorgensen.patienttrials.test.IntegrationTest;
import com.tajorgensen.patienttrials.utils.DrugTestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
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

@WebMvcTest(DrugController.class)
@ContextConfiguration(classes = {ObjectMapper.class, CreateDrugUseCase.class, GetDrugUseCase.class, DeleteDrugUseCase.class, UpdateDrugUseCase.class, DrugController.class, GlobalExceptionHandler.class})
@IntegrationTest
class DrugControllerIntTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DrugPort port;

    @Test
    void testCreateDrugWithValidationError() throws Exception {
        DrugWebModel webModel = DrugTestUtils.createWebModel();

        mockMvc.perform(post("/drugs").content(objectMapper.writeValueAsString(webModel)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(ErrorConstants.DrugErrorCode.INVALID_CREATE_REQUEST.getCode())));
    }

    @Test
    void testCreateDrug() throws Exception {
        DrugWebModel webModel = DrugTestUtils.createWebModel();
        webModel.setId(null);

        DrugWebModel webModelWithId = DrugTestUtils.createWebModel();
        when(port.createDrug(any())).thenReturn(DrugTestUtils.createDomain());

        mockMvc.perform(post("/drugs").content(objectMapper.writeValueAsString(webModel)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(webModelWithId)));
    }

    @Test
    void testUpdateDrugWithValidationErrorIdsDoNotMatch() throws Exception {
        DrugWebModel webModel = DrugTestUtils.createWebModel();

        mockMvc.perform(post("/drugs/1").content(objectMapper.writeValueAsString(webModel)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(ErrorConstants.DrugErrorCode.INVALID_UPDATE_REQUEST.getCode())));
    }

    @Test
    void testUpdateDrugWithValidationErrorIdInBodyIsNull() throws Exception {
        DrugWebModel webModel = DrugTestUtils.createWebModel();
        webModel.setId(null);
        mockMvc.perform(post("/drugs/1").content(objectMapper.writeValueAsString(webModel)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(ErrorConstants.DrugErrorCode.INVALID_UPDATE_REQUEST.getCode())));
    }

    @Test
    void testUpdateDrug() throws Exception {
        DrugWebModel webModel = DrugTestUtils.createWebModel();
        when(port.updateDrug(any())).thenReturn(DrugTestUtils.createDomain());

        mockMvc.perform(post("/drugs/" + webModel.getId().toString()).content(objectMapper.writeValueAsString(webModel)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(webModel)));
    }

    @Test
    void testGetDrugDoesNotExist() throws Exception {
        when(port.getDrugById(eq(1L))).thenThrow(new ResourceNotFoundException(ErrorConstants.DrugErrorCode.GET_ID_NOT_FOUND.getCode(), "Test"));

        mockMvc.perform(get("/drugs/1"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(StringUtils.EMPTY));
    }

    @Test
    void testGetDrug() throws Exception {
        DrugWebModel webModel = DrugTestUtils.createWebModel();
        when(port.getDrugById(eq(webModel.getId()))).thenReturn(DrugTestUtils.createDomain());

        mockMvc.perform(get("/drugs/" + webModel.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(webModel)));
    }

    @Test
    void testDeleteDrugDoesNotExist() throws Exception {
        doThrow(new ResourceNotFoundException(ErrorConstants.DrugErrorCode.DELETE_ID_NOT_FOUND.getCode(), "Test")).when(port).deleteDrugById(eq(1L));

        mockMvc.perform(delete("/drugs/1"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(StringUtils.EMPTY));
    }

    @Test
    void testDeleteDrug() throws Exception {
        DrugWebModel webModel = DrugTestUtils.createWebModel();
        when(port.getDrugById(eq(webModel.getId()))).thenReturn(DrugTestUtils.createDomain());

        mockMvc.perform(get("/drugs/" + webModel.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(webModel)));
    }

}