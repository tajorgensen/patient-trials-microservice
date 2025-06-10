package com.tajorgensen.patienttrials.utils;

import com.tajorgensen.patienttrials.adapter.model.TrialDrugAverageDosageListWebModel;
import com.tajorgensen.patienttrials.adapter.model.TrialDrugAverageDosageWebModel;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrialDrugAverageDosageTestUtils {

    public static TrialDrugAverageDosageListWebModel createListWebModel() {
        return TrialDrugAverageDosageListWebModel.builder()
                .trialDrugs(List.of(createWebModel()))
                .build();
    }

    public static TrialDrugAverageDosageWebModel createWebModel() {
        return TrialDrugAverageDosageWebModel.builder()
                .drugId(DrugTestUtils.ID.intValue())
                .drugName(DrugTestUtils.NAME)
                .trialId(TrialTestUtils.ID.intValue())
                .trialName(TrialTestUtils.NAME)
                .averageDosage(DrugTestUtils.DOSAGE)
                .dosageMeasurementType(DrugTestUtils.DOSAGE_MEASUREMENT_TYPE)
                .build();
    }

    public static void assertWebModelsEqual(TrialDrugAverageDosageWebModel expected, TrialDrugAverageDosageWebModel actual) {
        if (expected == null) {
            assertNull(actual);
            return;
        }

        assertAll(
                () -> assertEquals(expected.getDrugId(), actual.getDrugId()),
                () -> assertEquals(expected.getDrugName(), actual.getDrugName()),
                () -> assertEquals(expected.getTrialId(), actual.getTrialId()),
                () -> assertEquals(expected.getTrialName(), actual.getTrialName()),
                () -> assertTrue(expected.getAverageDosage().compareTo(actual.getAverageDosage()) == 0),
                () -> assertEquals(expected.getDosageMeasurementType(), actual.getDosageMeasurementType()),
                () -> assertEquals(expected.getPatientCount(), actual.getPatientCount())
        );
    }

}
