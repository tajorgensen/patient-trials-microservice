package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.common.exception.ErrorConstants;
import com.tajorgensen.patienttrials.common.exception.ResourceNotFoundException;
import com.tajorgensen.patienttrials.domain.model.Trial;
import com.tajorgensen.patienttrials.domain.port.TrialPort;
import com.tajorgensen.patienttrials.utils.TrialTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateTrialUseCaseTest {

    @Mock
    private TrialPort trialPort;

    @InjectMocks
    private UpdateTrialUseCase useCase;

    @Test
    void testExecute() {
        Trial domain = TrialTestUtils.createDomain();
        when(trialPort.updateTrial(eq(domain))).thenReturn(domain);

        Trial actualResult = useCase.execute(domain);

        TrialTestUtils.assertDomainModelsEqual(domain, actualResult);
    }

    @Test
    void testExecute_DoesNotExist() {
        Trial domain = TrialTestUtils.createDomain();
        when(trialPort.updateTrial(eq(domain))).thenThrow(new ResourceNotFoundException(ErrorConstants.TrialErrorCode.UPDATE_ID_NOT_FOUND.getCode(), "Test"));

        assertThrows(ResourceNotFoundException.class, () -> useCase.execute(domain));
    }
}