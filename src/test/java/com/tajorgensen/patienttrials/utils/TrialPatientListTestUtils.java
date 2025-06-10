package com.tajorgensen.patienttrials.utils;

import com.tajorgensen.patienttrials.adapter.model.PatientTrialStatusWebModel;
import com.tajorgensen.patienttrials.adapter.model.TrialPatientListWebModel;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;

public class TrialPatientListTestUtils {

    public static void assertWebModelsEqual(TrialPatientListWebModel expected, TrialPatientListWebModel actual) {
        if (expected == null) {
            assertNull(actual);
            return;
        }

        PatientTrialStatusTestUtils.assertWebModelListsAreEqual(expected.getPatients(), actual.getPatients());
    }

    public static TrialPatientListWebModel createWebModel() {
        PatientTrialStatusWebModel patientWebModel = PatientTrialStatusTestUtils.createWebModel();

        return TrialPatientListWebModel.builder()
                .patients(List.of(patientWebModel))
                .build();
    }
}
