package com.tajorgensen.patienttrials.adapter.transform;

import com.tajorgensen.patienttrials.adapter.model.PatientWebModel;
import com.tajorgensen.patienttrials.domain.model.Patient;
import com.tajorgensen.patienttrials.utils.PatientTestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class PatientWebModelTransformerTest {

    @Test
    void testFromDomainNull() {
        assertNull(PatientWebModelTransformer.fromDomain(null));
    }

    @Test
    void testFromDomainEmpty() {
        Patient domain = new Patient();
        PatientWebModel expected = new PatientWebModel();

        PatientWebModel actual = PatientWebModelTransformer.fromDomain(domain);

        PatientTestUtils.assertWebModelsEqual(expected, actual);
    }

    @Test
    void testFromDomain() {
        Patient domain = PatientTestUtils.createDomain();
        PatientWebModel expected = PatientTestUtils.createWebModel();

        PatientWebModel actual = PatientWebModelTransformer.fromDomain(domain);

        PatientTestUtils.assertWebModelsEqual(expected, actual);
    }

    @Test
    void testToDomainNull() {
        assertNull(PatientWebModelTransformer.toDomain(null));
    }

    @Test
    void testToDomainEmpty() {
        PatientWebModel webModel = new PatientWebModel();
        Patient expected = new Patient();

        Patient actual = PatientWebModelTransformer.toDomain(webModel);

        PatientTestUtils.assertDomainModelsEqual(expected, actual);
    }

    @Test
    void testToDomain() {
        PatientWebModel webModel = PatientTestUtils.createWebModel();
        Patient expected = PatientTestUtils.createDomain();

        Patient actual = PatientWebModelTransformer.toDomain(webModel);

        PatientTestUtils.assertDomainModelsEqual(expected, actual);
    }
}
