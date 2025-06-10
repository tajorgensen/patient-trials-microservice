package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.domain.model.PatientTrial;
import com.tajorgensen.patienttrials.domain.port.PatientTrialPort;
import com.tajorgensen.patienttrials.utils.PatientTestUtils;
import com.tajorgensen.patienttrials.utils.PatientTrialTestUtils;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPatientsForTrialUseCaseTest {

    @Mock
    private PatientTrialPort patientTrialPort;

    @InjectMocks
    private GetPatientsForTrialUseCase useCase;

    @Test
    void testGetAllPatientsTrialById() {
        PatientTrial domain = PatientTrialTestUtils.createDomain();
        domain.setDrugs(null);
        domain.setPatient(PatientTestUtils.createDomain());
        domain.setTrial(TrialTestUtils.createDomain());
        when(patientTrialPort.getAllPatientsByTrialId(eq(PatientTrialTestUtils.ID))).thenReturn(List.of(domain));

        List<PatientTrial> actualResultList = useCase.execute(PatientTrialTestUtils.ID);

        assertAll(
                () -> assertFalse(actualResultList.isEmpty()),
                () -> assertEquals(1, actualResultList.size()),
                () -> PatientTrialTestUtils.assertDomainModelsEqual(domain, actualResultList.get(0))
        );

    }

    @Test
    void testGetAllPatientsTrialById_DoesNotExist() {
        when(patientTrialPort.getAllPatientsByTrialId(anyLong())).thenReturn(Collections.emptyList());

        List<PatientTrial> actualResultList = useCase.execute(PatientTrialTestUtils.ID);

        assertAll(
                () -> assertNotNull(actualResultList),
                () -> assertEquals(0, actualResultList.size())
        );
    }

}