package com.tajorgensen.patienttrials.utils;

import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.AverageDrugDosageResultEntity;
import com.tajorgensen.patienttrials.domain.model.AverageDrugDosageResult;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AverageDrugDosageResultTestUtils {

    public static AverageDrugDosageResult createDomain() {
        return AverageDrugDosageResult.builder()
                .drugId(DrugTestUtils.ID.intValue())
                .drugName(DrugTestUtils.NAME)
                .trialId(TrialTestUtils.ID.intValue())
                .trialName(TrialTestUtils.NAME)
                .averageDosage(DrugTestUtils.DOSAGE)
                .dosageMeasurementType(DrugTestUtils.DOSAGE_MEASUREMENT_TYPE)
                .build();
    }

    public static AverageDrugDosageResultEntity createEntity() {
        return AverageDrugDosageResultEntity.builder()
                .drugId(DrugTestUtils.ID.intValue())
                .drugName(DrugTestUtils.NAME)
                .trialId(TrialTestUtils.ID.intValue())
                .trialName(TrialTestUtils.NAME)
                .averageDosage(DrugTestUtils.DOSAGE)
                .dosageMeasurementType(DrugTestUtils.DOSAGE_MEASUREMENT_TYPE.name())
                .build();
    }

    public static void assertDomainModelsEqual(AverageDrugDosageResult expected, AverageDrugDosageResult actual) {
        if (expected == null) {
            assertNull(actual);
            return;
        }

        assertAll(
                () -> assertEquals(expected.getDrugId(), actual.getDrugId()),
                () -> assertEquals(expected.getDrugName(), actual.getDrugName()),
                () -> assertEquals(expected.getTrialId(), actual.getTrialId()),
                () -> assertEquals(expected.getTrialName(), actual.getTrialName()),
                () -> assertEquals(expected.getAverageDosage(), actual.getAverageDosage()),
                () -> assertEquals(expected.getDosageMeasurementType(), actual.getDosageMeasurementType()),
                () -> assertEquals(expected.getPatientCount(), actual.getPatientCount())
        );
    }
}
