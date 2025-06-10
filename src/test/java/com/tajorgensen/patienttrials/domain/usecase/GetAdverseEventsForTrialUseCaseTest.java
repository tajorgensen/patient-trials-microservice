package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.domain.model.AdverseEvent;
import com.tajorgensen.patienttrials.domain.port.AdverseEventPort;
import com.tajorgensen.patienttrials.utils.AdverseEventTestUtils;
import com.tajorgensen.patienttrials.utils.TrialTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAdverseEventsForTrialUseCaseTest {

    @Mock
    private AdverseEventPort adverseEventPort;

    @InjectMocks
    private GetAdverseEventsForTrialUseCase useCase;

    @Test
    void testGetAdverseEventsByTrialId() {
        AdverseEvent domain = AdverseEventTestUtils.createDomain();
        when(adverseEventPort.getAdverseEventsByTrialId(eq(TrialTestUtils.ID))).thenReturn(List.of(domain));

        List<AdverseEvent> actualResult = useCase.execute(TrialTestUtils.ID);

        AdverseEventTestUtils.assertDomainModelsEqual(domain, actualResult.get(0));
    }

    @Test
    void testGetAdverseEventsByTrialId_DoesNotExist() {
        when(adverseEventPort.getAdverseEventsByTrialId(anyLong())).thenReturn(Collections.emptyList());

        List<AdverseEvent> actualResultList = useCase.execute(TrialTestUtils.ID);

        assertAll(
                () -> assertNotNull(actualResultList),
                () -> assertEquals(0, actualResultList.size())
        );
    }

}