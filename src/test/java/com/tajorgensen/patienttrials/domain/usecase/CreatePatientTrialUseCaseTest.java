package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.domain.model.PatientTrial;
import com.tajorgensen.patienttrials.domain.port.PatientTrialPort;
import com.tajorgensen.patienttrials.utils.PatientTrialTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreatePatientTrialUseCaseTest {

    @Mock
    private PatientTrialPort patientTrialPort;

    @InjectMocks
    private CreatePatientTrialUseCase useCase;

    @Test
    void testExecute() {
        PatientTrial domainWithId = PatientTrialTestUtils.createDomain();
        PatientTrial domainWithOutId = PatientTrialTestUtils.createDomain();
        domainWithOutId.setId(null);

        when(patientTrialPort.createPatientTrial(eq(domainWithOutId))).thenReturn(domainWithId);

        PatientTrial actualResult = useCase.execute(domainWithOutId);

        PatientTrialTestUtils.assertDomainModelsEqual(domainWithId, actualResult);
    }

}