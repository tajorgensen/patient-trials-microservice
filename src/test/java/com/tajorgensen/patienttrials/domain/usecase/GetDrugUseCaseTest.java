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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetDrugUseCaseTest {

    @Mock
    private DrugPort drugPort;

    @InjectMocks
    private GetDrugUseCase useCase;

    @Test
    void testGetDrugById() {
        Drug domain = DrugTestUtils.createDomain();
        when(drugPort.getDrugById(eq(DrugTestUtils.ID))).thenReturn(domain);

        Drug actualResult = useCase.execute(DrugTestUtils.ID);

        DrugTestUtils.assertDomainModelsEqual(domain, actualResult);
    }

    @Test
    void testGetDrugById_DoesNotExist() {
        when(drugPort.getDrugById(anyLong())).thenThrow(new ResourceNotFoundException(ErrorConstants.DrugErrorCode.GET_ID_NOT_FOUND.getCode(), "Test"));

        assertThrows(ResourceNotFoundException.class, () -> useCase.execute(DrugTestUtils.ID));
    }
}