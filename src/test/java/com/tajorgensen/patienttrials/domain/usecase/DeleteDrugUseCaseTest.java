package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.common.exception.ErrorConstants;
import com.tajorgensen.patienttrials.common.exception.ResourceNotFoundException;
import com.tajorgensen.patienttrials.domain.port.DrugPort;
import com.tajorgensen.patienttrials.utils.DrugTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteDrugUseCaseTest {

    @Mock
    private DrugPort drugPort;

    @InjectMocks
    private DeleteDrugUseCase useCase;

    @Test
    void testSuccessfulDelete() {
        assertDoesNotThrow(() -> useCase.execute(DrugTestUtils.ID));
        verify(drugPort, times(1)).deleteDrugById(DrugTestUtils.ID);
    }

    @Test
    void testDeleteDrug_DoesNotExist() {
        Mockito.doThrow(new ResourceNotFoundException(ErrorConstants.DrugErrorCode.DELETE_ID_NOT_FOUND.getCode(), "Test")).when(drugPort).deleteDrugById(eq(DrugTestUtils.ID));
        assertThrows(ResourceNotFoundException.class, () -> useCase.execute(DrugTestUtils.ID));
    }

}