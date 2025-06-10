package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.common.exception.ErrorConstants;
import com.tajorgensen.patienttrials.common.exception.ResourceNotFoundException;
import com.tajorgensen.patienttrials.domain.model.Drug;
import com.tajorgensen.patienttrials.domain.port.DrugPort;
import com.tajorgensen.patienttrials.utils.DrugTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateDrugUseCaseTest {

    @Mock
    private DrugPort drugPort;

    @InjectMocks
    private UpdateDrugUseCase useCase;

    @Test
    void testExecute() {
        Drug domain = DrugTestUtils.createDomain();
        when(drugPort.updateDrug(eq(domain))).thenReturn(domain);

        Drug actualResult = useCase.execute(domain);

        DrugTestUtils.assertDomainModelsEqual(domain, actualResult);
    }

    @Test
    void testExecute_DoesNotExist() {
        Drug domain = DrugTestUtils.createDomain();
        when(drugPort.updateDrug(eq(domain))).thenThrow(new ResourceNotFoundException(ErrorConstants.DrugErrorCode.UPDATE_ID_NOT_FOUND.getCode(), "Test"));

        assertThrows(ResourceNotFoundException.class, () -> useCase.execute(domain));
    }

}