package com.tajorgensen.patienttrials.adapter.transform;

import com.tajorgensen.patienttrials.adapter.model.TrialDrugAverageDosageListWebModel;
import com.tajorgensen.patienttrials.adapter.model.TrialDrugAverageDosageWebModel;
import com.tajorgensen.patienttrials.domain.model.AverageDrugDosageResult;
import com.tajorgensen.patienttrials.utils.AverageDrugDosageResultTestUtils;
import com.tajorgensen.patienttrials.utils.TrialDrugAverageDosageTestUtils;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TrialDrugAverageDosageListWebModelTransformerTest {

    @Test
    void testFromDomainNull() {
        TrialDrugAverageDosageListWebModel actual = TrialDrugAverageDosageListWebModelTransformer.fromDomain(null);

        assertAll(
                () -> assertNotNull(actual.getTrialDrugs()),
                () -> assertTrue(actual.getTrialDrugs().isEmpty())
        );
    }

    @Test
    void testFromDomainEmpty() {
        TrialDrugAverageDosageListWebModel actual = TrialDrugAverageDosageListWebModelTransformer.fromDomain(Collections.emptyList());

        assertAll(
                () -> assertNotNull(actual.getTrialDrugs()),
                () -> assertTrue(actual.getTrialDrugs().isEmpty())
        );
    }

    @Test
    void testFromDomain() {
        AverageDrugDosageResult domain = AverageDrugDosageResultTestUtils.createDomain();
        TrialDrugAverageDosageWebModel expected = TrialDrugAverageDosageTestUtils.createWebModel();

        TrialDrugAverageDosageListWebModel actual = TrialDrugAverageDosageListWebModelTransformer.fromDomain(List.of(domain));

        assertAll(
                () -> assertNotNull(actual.getTrialDrugs()),
                () -> assertFalse(actual.getTrialDrugs().isEmpty()),
                () -> assertEquals(1, actual.getTrialDrugs().size()),
                () -> TrialDrugAverageDosageTestUtils.assertWebModelsEqual(expected, actual.getTrialDrugs().get(0))
        );

    }

}