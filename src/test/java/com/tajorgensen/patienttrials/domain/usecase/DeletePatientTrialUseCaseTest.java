package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.common.exception.ErrorConstants;
import com.tajorgensen.patienttrials.common.exception.ResourceNotFoundException;
import com.tajorgensen.patienttrials.domain.port.PatientTrialPort;
import com.tajorgensen.patienttrials.utils.PatientTrialTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeletePatientTrialUseCaseTest {

    @Mock
    private PatientTrialPort patientTrialPort;

    @InjectMocks
    private DeletePatientTrialUseCase useCase;

    @Test
    void testSuccessfulDelete() {
        assertDoesNotThrow(() -> useCase.execute(PatientTrialTestUtils.ID));
        verify(patientTrialPort, times(1)).deletePatientTrialById(PatientTrialTestUtils.ID);
    }

    @Test
    void testDeletePatientTrial_DoesNotExist() {
        Mockito.doThrow(new ResourceNotFoundException(ErrorConstants.PatientTrialErrorCode.DELETE_ID_NOT_FOUND.getCode(), "Test")).when(patientTrialPort).deletePatientTrialById(eq(PatientTrialTestUtils.ID));
        assertThrows(ResourceNotFoundException.class, () -> useCase.execute(PatientTrialTestUtils.ID));
    }
}