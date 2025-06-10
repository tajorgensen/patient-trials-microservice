package com.tajorgensen.patienttrials.adapter.api;

import com.tajorgensen.patienttrials.adapter.model.AdverseEventWebModel;
import com.tajorgensen.patienttrials.common.exception.ErrorConstants;
import com.tajorgensen.patienttrials.common.exception.GlobalExceptionHandler;
import com.tajorgensen.patienttrials.common.exception.ResourceNotFoundException;
import com.tajorgensen.patienttrials.domain.port.AdverseEventPort;
import com.tajorgensen.patienttrials.domain.usecase.CreateAdverseEventUseCase;
import com.tajorgensen.patienttrials.domain.usecase.DeleteAdverseEventUseCase;
import com.tajorgensen.patienttrials.domain.usecase.GetAdverseEventUseCase;
import com.tajorgensen.patienttrials.domain.usecase.UpdateAdverseEventUseCase;
import com.tajorgensen.patienttrials.test.IntegrationTest;
import com.tajorgensen.patienttrials.utils.AdverseEventTestUtils;
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

@WebMvcTest(AdverseEventController.class)
@ContextConfiguration(classes = {ObjectMapper.class, CreateAdverseEventUseCase.class, GetAdverseEventUseCase.class, DeleteAdverseEventUseCase.class, UpdateAdverseEventUseCase.class, AdverseEventController.class, GlobalExceptionHandler.class})
@IntegrationTest
class AdverseEventControllerIntTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdverseEventPort port;

    @Test
    void testCreateAdverseEventWithValidationError() throws Exception {
        AdverseEventWebModel webModel = AdverseEventTestUtils.createWebModel();

        mockMvc.perform(post("/adverseEvents").content(objectMapper.writeValueAsString(webModel)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(ErrorConstants.AdverseEventErrorCode.INVALID_CREATE_REQUEST.getCode())));
    }

    @Test
    void testCreateAdverseEvent() throws Exception {
        AdverseEventWebModel webModel = AdverseEventTestUtils.createWebModel();
        webModel.setId(null);

        AdverseEventWebModel webModelWithId = AdverseEventTestUtils.createWebModel();
        when(port.createAdverseEvent(any())).thenReturn(AdverseEventTestUtils.createDomain());

        mockMvc.perform(post("/adverseEvents").content(objectMapper.writeValueAsString(webModel)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(webModelWithId)));
    }

    @Test
    void testUpdateAdverseEventWithValidationErrorIdsDoNotMatch() throws Exception {
        AdverseEventWebModel webModel = AdverseEventTestUtils.createWebModel();

        mockMvc.perform(post("/adverseEvents/1").content(objectMapper.writeValueAsString(webModel)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(ErrorConstants.AdverseEventErrorCode.INVALID_UPDATE_REQUEST.getCode())));
    }

    @Test
    void testUpdateAdverseEventWithValidationErrorIdInBodyIsNull() throws Exception {
        AdverseEventWebModel webModel = AdverseEventTestUtils.createWebModel();
        webModel.setId(null);
        mockMvc.perform(post("/adverseEvents/1").content(objectMapper.writeValueAsString(webModel)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(ErrorConstants.AdverseEventErrorCode.INVALID_UPDATE_REQUEST.getCode())));
    }

    @Test
    void testUpdateAdverseEvent() throws Exception {
        AdverseEventWebModel webModel = AdverseEventTestUtils.createWebModel();
        when(port.updateAdverseEvent(any())).thenReturn(AdverseEventTestUtils.createDomain());

        mockMvc.perform(post("/adverseEvents/" + webModel.getId().toString()).content(objectMapper.writeValueAsString(webModel)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(webModel)));
    }

    @Test
    void testGetAdverseEventDoesNotExist() throws Exception {
        when(port.getAdverseEventById(eq(1L))).thenThrow(new ResourceNotFoundException(ErrorConstants.AdverseEventErrorCode.GET_ID_NOT_FOUND.getCode(), "Test"));

        mockMvc.perform(get("/adverseEvents/1"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(StringUtils.EMPTY));
    }

    @Test
    void testGetAdverseEvent() throws Exception {
        AdverseEventWebModel webModel = AdverseEventTestUtils.createWebModel();
        when(port.getAdverseEventById(eq(webModel.getId()))).thenReturn(AdverseEventTestUtils.createDomain());

        mockMvc.perform(get("/adverseEvents/" + webModel.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(webModel)));
    }

    @Test
    void testDeleteAdverseEventDoesNotExist() throws Exception {
        doThrow(new ResourceNotFoundException(ErrorConstants.AdverseEventErrorCode.DELETE_ID_NOT_FOUND.getCode(), "Test")).when(port).deleteAdverseEventById(eq(1L));

        mockMvc.perform(delete("/adverseEvents/1"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(StringUtils.EMPTY));
    }

    @Test
    void testDeleteAdverseEvent() throws Exception {
        AdverseEventWebModel webModel = AdverseEventTestUtils.createWebModel();
        when(port.getAdverseEventById(eq(webModel.getId()))).thenReturn(AdverseEventTestUtils.createDomain());

        mockMvc.perform(get("/adverseEvents/" + webModel.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(webModel)));
    }

}