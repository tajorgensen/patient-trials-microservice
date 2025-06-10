package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.common.constants.ApplicationConstants;
import com.tajorgensen.patienttrials.common.exception.ErrorConstants;
import com.tajorgensen.patienttrials.common.exception.ResourceNotFoundException;
import com.tajorgensen.patienttrials.domain.model.Patient;
import com.tajorgensen.patienttrials.domain.port.PatientPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPatientUseCaseTest {

    private static final Long ID = 1234L;
    private static final String NAME = "Randy Savage";
    private static final LocalDate DATE_OF_BIRTH = LocalDate.now().minusYears(64);
    private static final ApplicationConstants.Gender GENDER = ApplicationConstants.Gender.MALE;
    private static final String MEDICAL_HISTORY = "1994-12-13: Heart attack due to build up fluid";

    @Mock
    private PatientPort patientPort;

    @InjectMocks
    private GetPatientUseCase useCase;

    @Test
    void testGetPatientById() {
        Patient domain = createDomain();
        when(patientPort.getPatientById(eq(ID))).thenReturn(domain);

        Patient actualResult = useCase.execute(ID);

        assertValuesUntouched(domain, actualResult);
    }

    @Test
    void testGetPatientById_DoesNotExist() {
        when(patientPort.getPatientById(anyLong())).thenThrow(new ResourceNotFoundException(ErrorConstants.PatientErrorCode.GET_ID_NOT_FOUND.getCode(), "Test"));

        assertThrows(ResourceNotFoundException.class, () -> useCase.execute(ID));
    }

    void assertValuesUntouched(Patient expected, Patient actual) {
        assertAll(
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getName(), actual.getName()),
                () -> assertEquals(expected.getDateOfBirth(), actual.getDateOfBirth()),
                () -> assertEquals(expected.getGender(), actual.getGender()),
                () -> assertEquals(expected.getMedicalHistory(), actual.getMedicalHistory())
        );
    }

    Patient createDomain() {
        return Patient.builder()
                .id(ID)
                .name(NAME)
                .dateOfBirth(DATE_OF_BIRTH)
                .gender(GENDER)
                .medicalHistory(MEDICAL_HISTORY)
                .build();
    }
}