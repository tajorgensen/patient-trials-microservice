package com.tajorgensen.patienttrials.adapter.gateway.client;

import com.tajorgensen.patienttrials.adapter.gateway.repository.TrialRepository;
import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.TrialEntity;
import com.tajorgensen.patienttrials.common.exception.ErrorConstants;
import com.tajorgensen.patienttrials.common.exception.ResourceNotFoundException;
import com.tajorgensen.patienttrials.domain.model.Trial;
import com.tajorgensen.patienttrials.utils.TrialTestUtils;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrialClientTest {

    @Mock
    private TrialRepository trialRepository;

    @InjectMocks
    private TrialClient trialClient;

    @Test
    void testCreateTrial() {
        TrialEntity trialEntity = TrialTestUtils.createEntity();

        when(trialRepository.save(eq(trialEntity))).thenReturn(trialEntity);

        Trial trial = TrialTestUtils.createDomain();

        Trial actualResult = trialClient.createTrial(trial);

        TrialTestUtils.assertDomainModelsEqual(trial, actualResult);
    }

    @Test
    void testGetTrialById() {
        TrialEntity trialEntity = TrialTestUtils.createEntity();

        when(trialRepository.findById(eq(TrialTestUtils.ID))).thenReturn(Optional.of(trialEntity));

        Trial expectedResult = TrialTestUtils.createDomain();

        Trial actualResult = trialClient.getTrialById(TrialTestUtils.ID);

        TrialTestUtils.assertDomainModelsEqual(expectedResult, actualResult);
    }

    @Test
    void testGetTrialById_DoesNotExist() {
        when(trialRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> trialClient.getTrialById(TrialTestUtils.ID));

        assertEquals(ErrorConstants.TrialErrorCode.GET_ID_NOT_FOUND.getCode(), ex.getErrorDetails().getErrorCode());
    }

    @Test
    void testUpdateTrial() {
        TrialEntity trialEntity = TrialTestUtils.createEntity();

        when(trialRepository.saveIfExists(eq(trialEntity))).thenReturn(trialEntity);

        Trial trial = TrialTestUtils.createDomain();

        Trial actualResult = trialClient.updateTrial(trial);

        TrialTestUtils.assertDomainModelsEqual(trial, actualResult);
    }

    @Test
    void testUpdateTrial_DoesNotExist() {
        TrialEntity trialEntity = TrialTestUtils.createEntity();
        when(trialRepository.saveIfExists(eq(trialEntity))).thenThrow(new JpaObjectRetrievalFailureException(new EntityNotFoundException()));

        Trial trial = TrialTestUtils.createDomain();

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> trialClient.updateTrial(trial));

        assertEquals(ErrorConstants.TrialErrorCode.UPDATE_ID_NOT_FOUND.getCode(), ex.getErrorDetails().getErrorCode());
    }

    @Test
    void testDeleteTrial() {
        assertDoesNotThrow(() -> trialClient.deleteTrialById(TrialTestUtils.ID));
        verify(trialRepository, times(1)).deleteById(TrialTestUtils.ID);
    }

    @Test
    void testDeleteTrial_DoesNotExist() {
        doNothing().when(trialRepository).deleteById(eq(TrialTestUtils.ID));
        assertDoesNotThrow(() -> trialClient.deleteTrialById(TrialTestUtils.ID));
    }

    @Test
    void testGetAllTrials() {
        TrialEntity trialEntity = TrialTestUtils.createEntity();
        when(trialRepository.findAll()).thenReturn(List.of(trialEntity));

        List<Trial> trialList = trialClient.getAllTrials();

        Trial expectedTrial = TrialTestUtils.createDomain();

        assertAll(
                () -> assertEquals(1, trialList.size()),
                () -> TrialTestUtils.assertDomainModelsEqual(expectedTrial, trialList.get(0))
        );
    }

    @Test
    void testGetAllTrials_NonePresent() {
        when(trialRepository.findAll()).thenReturn(Collections.emptyList());

        List<Trial> trialList = trialClient.getAllTrials();

        assertTrue(trialList.isEmpty());
    }
}