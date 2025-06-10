package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.domain.model.AdverseEvent;
import com.tajorgensen.patienttrials.domain.port.AdverseEventPort;
import com.tajorgensen.patienttrials.utils.AdverseEventTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateAdverseEventUseCaseTest {

    @Mock
    private AdverseEventPort adverseEventPort;

    @InjectMocks
    private CreateAdverseEventUseCase useCase;

    @Test
    void testExecute() {
        AdverseEvent domainWithId = AdverseEventTestUtils.createDomain();
        AdverseEvent domainWithOutId = AdverseEventTestUtils.createDomain();
        domainWithOutId.setId(null);

        when(adverseEventPort.createAdverseEvent(eq(domainWithOutId))).thenReturn(domainWithId);

        AdverseEvent actualResult = useCase.execute(domainWithOutId);

        AdverseEventTestUtils.assertDomainModelsEqual(domainWithId, actualResult);
    }

}