package com.tajorgensen.patienttrials.utils;

import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.PatientEntity;
import com.tajorgensen.patienttrials.adapter.model.PatientWebModel;
import com.tajorgensen.patienttrials.common.constants.ApplicationConstants;
import com.tajorgensen.patienttrials.domain.model.Patient;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PatientTestUtils {

    public static final Long ID = 1234L;
    private static final String NAME = "Randy Savage";
    private static final LocalDate DATE_OF_BIRTH = LocalDate.now().minusYears(64);
    private static final ApplicationConstants.Gender GENDER = ApplicationConstants.Gender.MALE;
    private static final String MEDICAL_HISTORY = "1994-12-13: Heart attack due to build up fluid";

    public static void assertWebModelsEqual(PatientWebModel expected, PatientWebModel actual) {
        if (expected == null) {
            assertNull(actual);
            return;
        }

        assertAll(
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getName(), actual.getName()),
                () -> assertEquals(expected.getDateOfBirth(), actual.getDateOfBirth()),
                () -> assertEquals(expected.getGender(), actual.getGender()),
                () -> assertEquals(expected.getMedicalHistory(), actual.getMedicalHistory())
        );
    }

    public static void assertDomainModelsEqual(Patient expected, Patient actual) {
        if (expected == null) {
            assertNull(actual);
            return;
        }

        assertAll(
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getName(), actual.getName()),
                () -> assertEquals(expected.getDateOfBirth(), actual.getDateOfBirth()),
                () -> assertEquals(expected.getGender(), actual.getGender()),
                () -> assertEquals(expected.getMedicalHistory(), actual.getMedicalHistory())
        );
    }

    public static void assertDomainModelListsAreEqual(List<Patient> expectedList, List<Patient> actualList) {
        if (expectedList == null) {
            assertNull(actualList);
            return;
        }

        assertNotNull(actualList);
        assertEquals(expectedList.size(), actualList.size());

        for (int i = 0; i < expectedList.size(); i++) {
            assertDomainModelsEqual(expectedList.get(i), actualList.get(i));
        }
    }

    public static void assertEntitiesEqual(PatientEntity expected, PatientEntity actual) {
        if (expected == null) {
            assertNull(actual);
            return;
        }

        assertAll(
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getName(), actual.getName()),
                () -> assertEquals(expected.getDateOfBirth(), actual.getDateOfBirth()),
                () -> assertEquals(expected.getGender(), actual.getGender()),
                () -> assertEquals(expected.getMedicalHistory(), actual.getMedicalHistory())
        );
    }

    public static PatientWebModel createWebModel() {
        return PatientWebModel.builder()
                .id(ID)
                .name(NAME)
                .dateOfBirth(DATE_OF_BIRTH)
                .gender(GENDER)
                .medicalHistory(MEDICAL_HISTORY)
                .build();
    }

    public static Patient createDomain() {
        return Patient.builder()
                .id(ID)
                .name(NAME)
                .dateOfBirth(DATE_OF_BIRTH)
                .gender(GENDER)
                .medicalHistory(MEDICAL_HISTORY)
                .build();
    }

    public static PatientEntity createEntity() {
        return PatientEntity.builder()
                .id(ID)
                .name(NAME)
                .dateOfBirth(DATE_OF_BIRTH)
                .gender(GENDER.getCode())
                .medicalHistory(MEDICAL_HISTORY)
                .build();
    }

}
