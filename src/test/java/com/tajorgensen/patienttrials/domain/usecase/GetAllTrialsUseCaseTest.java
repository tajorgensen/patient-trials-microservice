package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.domain.model.Trial;
import com.tajorgensen.patienttrials.domain.port.TrialPort;
import com.tajorgensen.patienttrials.utils.TrialTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllTrialsUseCaseTest {

    @Mock
    private TrialPort trialPort;

    @InjectMocks
    private GetAllTrialsUseCase useCase;

    @Test
    void testGetAllTrials() {
        Trial domain = TrialTestUtils.createDomain();
        when(trialPort.getAllTrials()).thenReturn(List.of(domain));

        List<Trial> actualResult = useCase.execute();

        TrialTestUtils.assertDomainModelsEqual(domain, actualResult.get(0));
    }

    @Test
    void testGetAllTrials_NoneExist() {
        when(trialPort.getAllTrials()).thenReturn(Collections.emptyList());

        List<Trial> actualResult = useCase.execute();

        assertTrue(actualResult.isEmpty());
    }

}