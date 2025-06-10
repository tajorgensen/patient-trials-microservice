package com.tajorgensen.patienttrials.utils;

import com.tajorgensen.patienttrials.adapter.model.PatientTrialStatusWebModel;
import com.tajorgensen.patienttrials.adapter.model.PatientWebModel;
import com.tajorgensen.patienttrials.domain.model.PatientTrial;
import org.junit.jupiter.api.Assertions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PatientTrialStatusTestUtils {

    public static PatientTrialStatusWebModel createWebModel() {
        PatientTrial patientTrial = PatientTrialTestUtils.createDomain();
        PatientWebModel patientWebModel = PatientTestUtils.createWebModel();

        return PatientTrialStatusWebModel.builder()
                .patientTrialId(patientTrial.getId())
                .patient(patientWebModel)
                .treatmentStatus(patientTrial.getTreatmentStatus())
                .enrollmentDate(patientTrial.getEnrollmentDate())
                .drugs(List.of(DrugTestUtils.createWebModel()))
                .build();
    }

    public static void assertWebModelsEqual(PatientTrialStatusWebModel expected, PatientTrialStatusWebModel actual) {
        if (expected == null) {
            assertNull(actual);
            return;
        }

        assertAll(
                () -> assertEquals(expected.getPatientTrialId(), actual.getPatientTrialId()),
                () -> PatientTestUtils.assertWebModelsEqual(expected.getPatient(), actual.getPatient()),
                () -> Assertions.assertEquals(expected.getTreatmentStatus(), actual.getTreatmentStatus()),
                () -> assertEquals(expected.getEnrollmentDate(), actual.getEnrollmentDate()),
                () -> DrugTestUtils.assertWebModelListsAreEqual(expected.getDrugs(), actual.getDrugs())
        );
    }

    public static void assertWebModelListsAreEqual(List<PatientTrialStatusWebModel> expectedList, List<PatientTrialStatusWebModel> actualList) {
        if (expectedList == null) {
            assertNull(actualList);
            return;
        }

        assertNotNull(actualList);
        assertEquals(expectedList.size(), actualList.size());

        for (int i = 0; i < expectedList.size(); i++) {
            assertWebModelsEqual(expectedList.get(i), actualList.get(i));
        }
    }

}
