package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.domain.model.Trial;
import com.tajorgensen.patienttrials.domain.port.TrialPort;
import com.tajorgensen.patienttrials.utils.TrialTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateTrialUseCaseTest {

    @Mock
    private TrialPort trialPort;

    @InjectMocks
    private CreateTrialUseCase useCase;

    @Test
    void testExecute() {
        Trial domainWithId = TrialTestUtils.createDomain();
        Trial domainWithOutId = TrialTestUtils.createDomain();
        domainWithOutId.setId(null);

        when(trialPort.createTrial(eq(domainWithOutId))).thenReturn(domainWithId);

        Trial actualResult = useCase.execute(domainWithOutId);

        TrialTestUtils.assertDomainModelsEqual(domainWithId, actualResult);
    }
}