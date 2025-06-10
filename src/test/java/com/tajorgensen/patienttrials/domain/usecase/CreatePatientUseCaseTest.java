package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.domain.model.Patient;
import com.tajorgensen.patienttrials.domain.port.PatientPort;
import com.tajorgensen.patienttrials.utils.PatientTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreatePatientUseCaseTest {

    @Mock
    private PatientPort patientPort;

    @InjectMocks
    private CreatePatientUseCase useCase;

    @Test
    void testExecute() {
        Patient domainWithId = PatientTestUtils.createDomain();
        Patient domainWithOutId = PatientTestUtils.createDomain();
        domainWithOutId.setId(null);

        when(patientPort.createPatient(eq(domainWithOutId))).thenReturn(domainWithId);

        Patient actualResult = useCase.execute(domainWithOutId);

        assertValuesUntouchedWithNewId(domainWithId, actualResult);
    }

    void assertValuesUntouchedWithNewId(Patient expected, Patient actual) {
        assertAll(
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getName(), actual.getName()),
                () -> assertEquals(expected.getDateOfBirth(), actual.getDateOfBirth()),
                () -> assertEquals(expected.getGender(), actual.getGender()),
                () -> assertEquals(expected.getMedicalHistory(), actual.getMedicalHistory())
        );
    }

}