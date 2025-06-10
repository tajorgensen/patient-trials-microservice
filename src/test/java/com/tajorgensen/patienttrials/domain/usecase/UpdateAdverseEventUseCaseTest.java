package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.common.exception.ErrorConstants;
import com.tajorgensen.patienttrials.common.exception.ResourceNotFoundException;
import com.tajorgensen.patienttrials.domain.model.AdverseEvent;
import com.tajorgensen.patienttrials.domain.port.AdverseEventPort;
import com.tajorgensen.patienttrials.utils.AdverseEventTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateAdverseEventUseCaseTest {

    @Mock
    private AdverseEventPort adverseEventPort;

    @InjectMocks
    private UpdateAdverseEventUseCase useCase;

    @Test
    void testExecute() {
        AdverseEvent domain = AdverseEventTestUtils.createDomain();
        when(adverseEventPort.updateAdverseEvent(eq(domain))).thenReturn(domain);

        AdverseEvent actualResult = useCase.execute(domain);

        AdverseEventTestUtils.assertDomainModelsEqual(domain, actualResult);
    }

    @Test
    void testExecute_DoesNotExist() {
        AdverseEvent domain = AdverseEventTestUtils.createDomain();
        when(adverseEventPort.updateAdverseEvent(eq(domain))).thenThrow(new ResourceNotFoundException(ErrorConstants.AdverseEventErrorCode.UPDATE_ID_NOT_FOUND.getCode(), "Test"));

        assertThrows(ResourceNotFoundException.class, () -> useCase.execute(domain));
    }

}