package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.common.exception.ErrorConstants;
import com.tajorgensen.patienttrials.common.exception.ResourceNotFoundException;
import com.tajorgensen.patienttrials.domain.port.AdverseEventPort;
import com.tajorgensen.patienttrials.utils.AdverseEventTestUtils;
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
class DeleteAdverseEventUseCaseTest {

    @Mock
    private AdverseEventPort adverseEventPort;

    @InjectMocks
    private DeleteAdverseEventUseCase useCase;

    @Test
    void testSuccessfulDelete() {
        assertDoesNotThrow(() -> useCase.execute(AdverseEventTestUtils.ID));
        verify(adverseEventPort, times(1)).deleteAdverseEventById(AdverseEventTestUtils.ID);
    }

    @Test
    void testDeleteAdverseEvent_DoesNotExist() {
        Mockito.doThrow(new ResourceNotFoundException(ErrorConstants.AdverseEventErrorCode.DELETE_ID_NOT_FOUND.getCode(), "Test")).when(adverseEventPort).deleteAdverseEventById(eq(AdverseEventTestUtils.ID));
        assertThrows(ResourceNotFoundException.class, () -> useCase.execute(AdverseEventTestUtils.ID));
    }

}