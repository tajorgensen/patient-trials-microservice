package com.tajorgensen.patienttrials.adapter.gateway.client;

import com.tajorgensen.patienttrials.adapter.gateway.repository.CalculateAverageDosageRepository;
import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.AverageDrugDosageResultEntity;
import com.tajorgensen.patienttrials.domain.model.AverageDrugDosageResult;
import com.tajorgensen.patienttrials.utils.AverageDrugDosageResultTestUtils;
import com.tajorgensen.patienttrials.utils.TrialTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculateAverageDosageClientTest {

    @Mock
    private CalculateAverageDosageRepository calculateAverageDosageRepository;

    @InjectMocks
    private CalculateAverageDosageClient calculateAverageDosageClient;

    @Test
    void testGetAverageDosageForTrialId() {
        AverageDrugDosageResult domain = AverageDrugDosageResultTestUtils.createDomain();
        AverageDrugDosageResultEntity entity = AverageDrugDosageResultTestUtils.createEntity();

        when(calculateAverageDosageRepository.calculateAverageDosage(ArgumentMatchers.eq(TrialTestUtils.ID.intValue()))).thenReturn(List.of(entity));

        List<AverageDrugDosageResult> resultList = calculateAverageDosageClient.getAverageDosageForTrialId(TrialTestUtils.ID);

        assertAll(
                () -> assertFalse(resultList.isEmpty()),
                () -> assertEquals(1, resultList.size()),
                () -> AverageDrugDosageResultTestUtils.assertDomainModelsEqual(domain, resultList.get(0))
        );
    }

    @Test
    void testGetAverageDosageForTrialId_NoResults() {
        when(calculateAverageDosageRepository.calculateAverageDosage(eq(TrialTestUtils.ID.intValue()))).thenReturn(Collections.emptyList());

        List<AverageDrugDosageResult> resultList = calculateAverageDosageClient.getAverageDosageForTrialId(TrialTestUtils.ID);

        assertAll(
                () -> assertNotNull(resultList),
                () -> assertTrue(resultList.isEmpty())
        );
    }

}