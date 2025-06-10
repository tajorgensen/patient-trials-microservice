package com.tajorgensen.patienttrials.adapter.transform;

import com.tajorgensen.patienttrials.adapter.model.TrialDrugAverageDosageWebModel;
import com.tajorgensen.patienttrials.domain.model.AverageDrugDosageResult;
import com.tajorgensen.patienttrials.utils.AverageDrugDosageResultTestUtils;
import com.tajorgensen.patienttrials.utils.TrialDrugAverageDosageTestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class TrialDrugAverageDosageWebModelTransformerTest {

    @Test
    void testFromDomainNull() {
        assertNull(TrialDrugAverageDosageWebModelTransformer.fromDomain(null));
    }

    @Test
    void testFromDomain() {
        AverageDrugDosageResult domain = AverageDrugDosageResultTestUtils.createDomain();
        TrialDrugAverageDosageWebModel expected = TrialDrugAverageDosageTestUtils.createWebModel();

        TrialDrugAverageDosageWebModel actual = TrialDrugAverageDosageWebModelTransformer.fromDomain(domain);

        TrialDrugAverageDosageTestUtils.assertWebModelsEqual(expected, actual);
    }

}