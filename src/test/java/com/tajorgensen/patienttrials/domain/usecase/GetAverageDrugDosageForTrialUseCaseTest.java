package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.domain.model.AverageDrugDosageResult;
import com.tajorgensen.patienttrials.domain.port.GetAverageDosagePort;
import com.tajorgensen.patienttrials.utils.AverageDrugDosageResultTestUtils;
import com.tajorgensen.patienttrials.utils.TrialTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
class GetAverageDrugDosageForTrialUseCaseTest {

    @Mock
    private GetAverageDosagePort getAverageDosagePort;

    @InjectMocks
    private GetAverageDrugDosageForTrialUseCase getAverageDrugDosageForTrialUseCase;

    @Test
    void testGetAverageDosageForTrialId() {
        AverageDrugDosageResult domain = AverageDrugDosageResultTestUtils.createDomain();

        when(getAverageDosagePort.getAverageDosageForTrialId(eq(TrialTestUtils.ID))).thenReturn(List.of(domain));

        List<AverageDrugDosageResult> resultList = getAverageDrugDosageForTrialUseCase.execute(TrialTestUtils.ID);

        assertAll(
                () -> assertFalse(resultList.isEmpty()),
                () -> assertEquals(1, resultList.size()),
                () -> AverageDrugDosageResultTestUtils.assertDomainModelsEqual(domain, resultList.get(0))
        );
    }

    @Test
    void testGetAverageDosageForTrialId_NoResults() {
        when(getAverageDosagePort.getAverageDosageForTrialId(eq(TrialTestUtils.ID))).thenReturn(Collections.emptyList());

        List<AverageDrugDosageResult> resultList = getAverageDrugDosageForTrialUseCase.execute(TrialTestUtils.ID);

        assertAll(
                () -> assertNotNull(resultList),
                () -> assertTrue(resultList.isEmpty())
        );
    }
}