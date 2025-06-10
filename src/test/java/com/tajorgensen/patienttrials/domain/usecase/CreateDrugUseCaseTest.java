package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.domain.model.Drug;
import com.tajorgensen.patienttrials.domain.port.DrugPort;
import com.tajorgensen.patienttrials.utils.DrugTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateDrugUseCaseTest {

    @Mock
    private DrugPort drugPort;

    @InjectMocks
    private CreateDrugUseCase useCase;

    @Test
    void testExecute() {
        Drug domainWithId = DrugTestUtils.createDomain();
        Drug domainWithOutId = DrugTestUtils.createDomain();
        domainWithOutId.setId(null);

        when(drugPort.createDrug(eq(domainWithOutId))).thenReturn(domainWithId);

        Drug actualResult = useCase.execute(domainWithOutId);

        DrugTestUtils.assertDomainModelsEqual(domainWithId, actualResult);
    }

}