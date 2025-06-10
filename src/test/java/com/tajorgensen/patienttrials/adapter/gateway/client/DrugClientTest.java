package com.tajorgensen.patienttrials.adapter.gateway.client;

import com.tajorgensen.patienttrials.adapter.gateway.repository.DrugRepository;
import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.DrugEntity;
import com.tajorgensen.patienttrials.common.exception.ErrorConstants;
import com.tajorgensen.patienttrials.common.exception.ResourceNotFoundException;
import com.tajorgensen.patienttrials.domain.model.Drug;
import com.tajorgensen.patienttrials.utils.DrugTestUtils;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DrugClientTest {

    @Mock
    private DrugRepository drugRepository;

    @InjectMocks
    private DrugClient drugClient;

    @Test
    void testCreateDrug() {
        DrugEntity drugEntity = DrugTestUtils.createEntity();

        when(drugRepository.save(eq(drugEntity))).thenReturn(drugEntity);

        Drug drug = DrugTestUtils.createDomain();

        Drug actualResult = drugClient.createDrug(drug);

        DrugTestUtils.assertDomainModelsEqual(drug, actualResult);
    }

    @Test
    void testGetDrugById() {
        DrugEntity drugEntity = DrugTestUtils.createEntity();

        when(drugRepository.findById(eq(DrugTestUtils.ID))).thenReturn(Optional.of(drugEntity));

        Drug expectedResult = DrugTestUtils.createDomain();

        Drug actualResult = drugClient.getDrugById(DrugTestUtils.ID);

        DrugTestUtils.assertDomainModelsEqual(expectedResult, actualResult);
    }

    @Test
    void testGetDrugById_DoesNotExist() {
        when(drugRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> drugClient.getDrugById(DrugTestUtils.ID));

        assertEquals(ErrorConstants.DrugErrorCode.GET_ID_NOT_FOUND.getCode(), ex.getErrorDetails().getErrorCode());
    }

    @Test
    void testUpdateDrug() {
        DrugEntity drugEntity = DrugTestUtils.createEntity();

        when(drugRepository.saveIfExists(eq(drugEntity))).thenReturn(drugEntity);

        Drug drug = DrugTestUtils.createDomain();

        Drug actualResult = drugClient.updateDrug(drug);

        DrugTestUtils.assertDomainModelsEqual(drug, actualResult);
    }

    @Test
    void testUpdateDrug_DoesNotExist() {
        DrugEntity drugEntity = DrugTestUtils.createEntity();
        when(drugRepository.saveIfExists(eq(drugEntity))).thenThrow(new JpaObjectRetrievalFailureException(new EntityNotFoundException()));

        Drug drug = DrugTestUtils.createDomain();

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> drugClient.updateDrug(drug));

        assertEquals(ErrorConstants.DrugErrorCode.UPDATE_ID_NOT_FOUND.getCode(), ex.getErrorDetails().getErrorCode());
    }

    @Test
    void testDeleteDrug() {
        assertDoesNotThrow(() -> drugClient.deleteDrugById(DrugTestUtils.ID));
        verify(drugRepository, times(1)).deleteById(DrugTestUtils.ID);
    }

    @Test
    void testDeleteDrug_DoesNotExist() {
        doNothing().when(drugRepository).deleteById(eq(DrugTestUtils.ID));
        assertDoesNotThrow(() -> drugClient.deleteDrugById(DrugTestUtils.ID));
    }

}