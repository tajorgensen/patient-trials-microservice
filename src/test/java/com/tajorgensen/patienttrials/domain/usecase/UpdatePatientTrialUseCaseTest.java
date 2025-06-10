package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.common.exception.ErrorConstants;
import com.tajorgensen.patienttrials.common.exception.ResourceNotFoundException;
import com.tajorgensen.patienttrials.domain.model.PatientTrial;
import com.tajorgensen.patienttrials.domain.port.PatientTrialPort;
import com.tajorgensen.patienttrials.utils.PatientTrialTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdatePatientTrialUseCaseTest {

    @Mock
    private PatientTrialPort patientTrialPort;

    @InjectMocks
    private UpdatePatientTrialUseCase useCase;

    @Test
    void testExecute() {
        PatientTrial domain = PatientTrialTestUtils.createDomain();
        when(patientTrialPort.updatePatientTrial(eq(domain))).thenReturn(domain);

        PatientTrial actualResult = useCase.execute(domain);

        PatientTrialTestUtils.assertDomainModelsEqual(domain, actualResult);
    }

    @Test
    void testExecute_DoesNotExist() {
        PatientTrial domain = PatientTrialTestUtils.createDomain();
        when(patientTrialPort.updatePatientTrial(eq(domain))).thenThrow(new ResourceNotFoundException(ErrorConstants.PatientTrialErrorCode.UPDATE_ID_NOT_FOUND.getCode(), "Test"));

        assertThrows(ResourceNotFoundException.class, () -> useCase.execute(domain));
    }

}