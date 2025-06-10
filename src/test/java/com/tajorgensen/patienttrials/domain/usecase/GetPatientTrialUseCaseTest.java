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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPatientTrialUseCaseTest {

    @Mock
    private PatientTrialPort patientTrialPort;

    @InjectMocks
    private GetPatientTrialUseCase useCase;

    @Test
    void testGetPatientTrialById() {
        PatientTrial domain = PatientTrialTestUtils.createDomain();
        when(patientTrialPort.getPatientTrialById(eq(PatientTrialTestUtils.ID))).thenReturn(domain);

        PatientTrial actualResult = useCase.execute(PatientTrialTestUtils.ID);

        PatientTrialTestUtils.assertDomainModelsEqual(domain, actualResult);
    }

    @Test
    void testGetPatientTrialById_DoesNotExist() {
        when(patientTrialPort.getPatientTrialById(anyLong())).thenThrow(new ResourceNotFoundException(ErrorConstants.PatientTrialErrorCode.GET_ID_NOT_FOUND.getCode(), "Test"));

        assertThrows(ResourceNotFoundException.class, () -> useCase.execute(PatientTrialTestUtils.ID));
    }
}