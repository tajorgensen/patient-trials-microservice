package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.common.exception.ErrorConstants;
import com.tajorgensen.patienttrials.common.exception.ResourceNotFoundException;
import com.tajorgensen.patienttrials.domain.port.TrialPort;
import com.tajorgensen.patienttrials.utils.TrialTestUtils;
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
class DeleteTrialUseCaseTest {

    @Mock
    private TrialPort trialPort;

    @InjectMocks
    private DeleteTrialUseCase useCase;

    @Test
    void testSuccessfulDelete() {
        assertDoesNotThrow(() -> useCase.execute(TrialTestUtils.ID));
        verify(trialPort, times(1)).deleteTrialById(TrialTestUtils.ID);
    }

    @Test
    void testDeleteTrial_DoesNotExist() {
        Mockito.doThrow(new ResourceNotFoundException(ErrorConstants.TrialErrorCode.DELETE_ID_NOT_FOUND.getCode(), "Test")).when(trialPort).deleteTrialById(eq(TrialTestUtils.ID));
        assertThrows(ResourceNotFoundException.class, () -> useCase.execute(TrialTestUtils.ID));
    }
}