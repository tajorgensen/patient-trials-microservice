package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.common.exception.ErrorConstants;
import com.tajorgensen.patienttrials.common.exception.ResourceNotFoundException;
import com.tajorgensen.patienttrials.domain.port.PatientPort;
import com.tajorgensen.patienttrials.utils.PatientTestUtils;
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
class DeletePatientUseCaseTest {

    @Mock
    private PatientPort patientPort;

    @InjectMocks
    private DeletePatientUseCase useCase;

    @Test
    void testSuccessfulDelete() {
        assertDoesNotThrow(() -> useCase.execute(PatientTestUtils.ID));
        verify(patientPort, times(1)).deletePatientById(PatientTestUtils.ID);
    }

    @Test
    void testDeletePatient_DoesNotExist() {
        Mockito.doThrow(new ResourceNotFoundException(ErrorConstants.PatientErrorCode.DELETE_ID_NOT_FOUND.getCode(), "Test")).when(patientPort).deletePatientById(eq(PatientTestUtils.ID));
        assertThrows(ResourceNotFoundException.class, () -> useCase.execute(PatientTestUtils.ID));
    }

}