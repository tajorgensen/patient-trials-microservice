package com.tajorgensen.patienttrials.adapter.transform;

import com.tajorgensen.patienttrials.adapter.model.PatientTrialWebModel;
import com.tajorgensen.patienttrials.domain.model.PatientTrial;
import com.tajorgensen.patienttrials.utils.PatientTrialTestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class PatientTrialWebModelTransformerTest {

    @Test
    void testFromDomainNull() {
        assertNull(PatientTrialWebModelTransformer.fromDomain(null));
    }

    @Test
    void testFromDomainEmpty() {
        PatientTrial domain = new PatientTrial();
        PatientTrialWebModel expected = new PatientTrialWebModel();

        PatientTrialWebModel actual = PatientTrialWebModelTransformer.fromDomain(domain);

        PatientTrialTestUtils.assertWebModelsEqual(expected, actual);
    }

    @Test
    void testFromDomain() {
        PatientTrial domain = PatientTrialTestUtils.createDomain();
        PatientTrialWebModel expected = PatientTrialTestUtils.createWebModel();

        PatientTrialWebModel actual = PatientTrialWebModelTransformer.fromDomain(domain);

        PatientTrialTestUtils.assertWebModelsEqual(expected, actual);
    }

    @Test
    void testToDomainNull() {
        assertNull(PatientTrialWebModelTransformer.toDomain(null));
    }

    @Test
    void testToDomainEmpty() {
        PatientTrialWebModel webModel = new PatientTrialWebModel();
        PatientTrial expected = new PatientTrial();

        PatientTrial actual = PatientTrialWebModelTransformer.toDomain(webModel);

        PatientTrialTestUtils.assertDomainModelsEqual(expected, actual);
    }

    @Test
    void testToDomain() {
        PatientTrialWebModel webModel = PatientTrialTestUtils.createWebModel();
        PatientTrial expected = PatientTrialTestUtils.createDomain();

        PatientTrial actual = PatientTrialWebModelTransformer.toDomain(webModel);

        PatientTrialTestUtils.assertDomainModelsEqual(expected, actual);
    }

}