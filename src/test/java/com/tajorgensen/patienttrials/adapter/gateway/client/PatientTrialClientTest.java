package com.tajorgensen.patienttrials.adapter.gateway.client;

import com.tajorgensen.patienttrials.adapter.gateway.repository.PatientTrialDrugRepository;
import com.tajorgensen.patienttrials.adapter.gateway.repository.PatientTrialRepository;
import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.PatientTrialDrugEntity;
import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.PatientTrialEntity;
import com.tajorgensen.patienttrials.common.exception.ErrorConstants;
import com.tajorgensen.patienttrials.common.exception.ResourceNotFoundException;
import com.tajorgensen.patienttrials.domain.model.Drug;
import com.tajorgensen.patienttrials.domain.model.PatientTrial;
import com.tajorgensen.patienttrials.utils.DrugTestUtils;
import com.tajorgensen.patienttrials.utils.PatientTrialTestUtils;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientTrialClientTest {

    @Mock
    private PatientTrialRepository patientTrialRepository;

    @Mock
    private PatientTrialDrugRepository patientTrialDrugRepository;

    @InjectMocks
    private PatientTrialClient patientTrialClient;

    @Test
    void testCreatePatientTrial() {
        PatientTrialEntity patientTrialEntityWithId = PatientTrialTestUtils.createEntity();
        PatientTrialEntity patientTrialEntityWithoutId = PatientTrialTestUtils.createEntity();
        PatientTrialDrugEntity patientTrialDrugEntityWithId = DrugTestUtils.createPatientTrialDrugEntity();
        PatientTrialDrugEntity patientTrialDrugEntityWithoutId = DrugTestUtils.createPatientTrialDrugEntity();

        patientTrialEntityWithoutId.setId(null);
        patientTrialDrugEntityWithoutId.setId(null);
        patientTrialDrugEntityWithId.setDrug(DrugTestUtils.createEntity());

        when(patientTrialRepository.save(eq(patientTrialEntityWithoutId))).thenReturn(patientTrialEntityWithId);
        when(patientTrialDrugRepository.save(eq(patientTrialDrugEntityWithoutId))).thenReturn(patientTrialDrugEntityWithId);

        PatientTrial patientTrial = PatientTrialTestUtils.createDomain();
        patientTrial.setId(null);
        patientTrial.setDrugs(List.of(DrugTestUtils.createDrugFromPatientTrialDrug()));

        PatientTrial actualResult = patientTrialClient.createPatientTrial(patientTrial);

        patientTrial.setId(PatientTrialTestUtils.ID);

        PatientTrialTestUtils.assertDomainModelsEqual(patientTrial, actualResult);
    }

    @Test
    void testGetPatientTrialById() {
        PatientTrialEntity patientTrialEntity = PatientTrialTestUtils.createEntity();
        PatientTrialDrugEntity patientTrialDrugEntity = DrugTestUtils.createPatientTrialDrugEntity();
        patientTrialDrugEntity.setDrug(DrugTestUtils.createEntity());
        Drug patientTrialDrug = DrugTestUtils.createDrugFromPatientTrialDrug();

        when(patientTrialRepository.findById(eq(PatientTrialTestUtils.ID))).thenReturn(Optional.of(patientTrialEntity));
        when(patientTrialDrugRepository.findAllByPatientTrialId(eq(PatientTrialTestUtils.ID))).thenReturn(List.of(patientTrialDrugEntity));

        PatientTrial expectedResult = PatientTrialTestUtils.createDomain();
        expectedResult.setDrugs(List.of(patientTrialDrug));

        PatientTrial actualResult = patientTrialClient.getPatientTrialById(PatientTrialTestUtils.ID);

        PatientTrialTestUtils.assertDomainModelsEqual(expectedResult, actualResult);
    }

    @Test
    void testGetPatientTrialById_DoesNotExist() {
        when(patientTrialRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> patientTrialClient.getPatientTrialById(PatientTrialTestUtils.ID));

        assertEquals(ErrorConstants.PatientTrialErrorCode.GET_ID_NOT_FOUND.getCode(), ex.getErrorDetails().getErrorCode());
    }

    @Test
    void testUpdatePatientTrial() {
        PatientTrialEntity patientTrialEntity = PatientTrialTestUtils.createEntity();

        when(patientTrialRepository.saveIfExists(eq(patientTrialEntity))).thenReturn(patientTrialEntity);

        PatientTrial patientTrial = PatientTrialTestUtils.createDomain();
        patientTrial.setDrugs(List.of(DrugTestUtils.createDrugFromPatientTrialDrug()));

        PatientTrial actualResult = patientTrialClient.updatePatientTrial(patientTrial);

        patientTrial.setDrugs(null);

        PatientTrialTestUtils.assertDomainModelsEqual(patientTrial, actualResult);

        verifyNoInteractions(patientTrialDrugRepository);
    }

    @Test
    void testUpdatePatientTrial_DoesNotExist() {
        PatientTrialEntity patientTrialEntity = PatientTrialTestUtils.createEntity();
        when(patientTrialRepository.saveIfExists(eq(patientTrialEntity))).thenThrow(new JpaObjectRetrievalFailureException(new EntityNotFoundException()));

        PatientTrial patientTrial = PatientTrialTestUtils.createDomain();

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> patientTrialClient.updatePatientTrial(patientTrial));

        assertEquals(ErrorConstants.PatientTrialErrorCode.UPDATE_ID_NOT_FOUND.getCode(), ex.getErrorDetails().getErrorCode());
    }

    @Test
    void testDeletePatientTrial() {
        when(patientTrialDrugRepository.deleteAllByPatientTrialId(PatientTrialTestUtils.ID)).thenReturn(1);

        assertDoesNotThrow(() -> patientTrialClient.deletePatientTrialById(PatientTrialTestUtils.ID));
        verify(patientTrialRepository, times(1)).deleteById(PatientTrialTestUtils.ID);
        verify(patientTrialDrugRepository, times(1)).deleteAllByPatientTrialId(PatientTrialTestUtils.ID);
    }

    @Test
    void testDeletePatientTrial_DoesNotExist() {
        when(patientTrialDrugRepository.deleteAllByPatientTrialId(PatientTrialTestUtils.ID)).thenReturn(1);

        doNothing().when(patientTrialRepository).deleteById(eq(PatientTrialTestUtils.ID));
        assertDoesNotThrow(() -> patientTrialClient.deletePatientTrialById(PatientTrialTestUtils.ID));
    }

    @Test
    void testGetAllPatientsByTrialId() {
        PatientTrialEntity patientTrialEntity = PatientTrialTestUtils.createEntity();
        PatientTrial patientTrial = PatientTrialTestUtils.createDomain();
        patientTrial.setDrugs(null);

        when(patientTrialRepository.findByTrialIdWithEagerRelationships(TrialTestUtils.ID)).thenReturn(List.of(patientTrialEntity));

        List<PatientTrial> patientTrialList = patientTrialClient.getAllPatientsByTrialId(TrialTestUtils.ID);


        assertAll(
                () -> assertFalse(patientTrialList.isEmpty()),
                () -> assertEquals(1, patientTrialList.size()),
                () -> PatientTrialTestUtils.assertDomainModelsEqual(patientTrial, patientTrialList.get(0))
        );
    }

    @Test
    void testGetAllPatientsByTrialId_NonePresent() {
        when(patientTrialRepository.findByTrialIdWithEagerRelationships(TrialTestUtils.ID)).thenReturn(Collections.emptyList());

        List<PatientTrial> patientTrialList = patientTrialClient.getAllPatientsByTrialId(TrialTestUtils.ID);

        assertAll(
                () -> assertNotNull(patientTrialList),
                () -> assertTrue(patientTrialList.isEmpty())
        );
    }


}