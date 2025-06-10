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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAdverseEventUseCaseTest {

    @Mock
    private AdverseEventPort adverseEventPort;

    @InjectMocks
    private GetAdverseEventUseCase useCase;

    @Test
    void testGetAdverseEventById() {
        AdverseEvent domain = AdverseEventTestUtils.createDomain();
        when(adverseEventPort.getAdverseEventById(eq(AdverseEventTestUtils.ID))).thenReturn(domain);

        AdverseEvent actualResult = useCase.execute(AdverseEventTestUtils.ID);

        AdverseEventTestUtils.assertDomainModelsEqual(domain, actualResult);
    }

    @Test
    void testGetAdverseEventById_DoesNotExist() {
        when(adverseEventPort.getAdverseEventById(anyLong())).thenThrow(new ResourceNotFoundException(ErrorConstants.AdverseEventErrorCode.GET_ID_NOT_FOUND.getCode(), "Test"));

        assertThrows(ResourceNotFoundException.class, () -> useCase.execute(AdverseEventTestUtils.ID));
    }

}