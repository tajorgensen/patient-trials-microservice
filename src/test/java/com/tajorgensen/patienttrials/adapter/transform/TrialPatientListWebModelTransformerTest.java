package com.tajorgensen.patienttrials.adapter.transform;

import com.tajorgensen.patienttrials.adapter.model.TrialPatientListWebModel;
import com.tajorgensen.patienttrials.domain.model.PatientTrial;
import com.tajorgensen.patienttrials.utils.PatientTestUtils;
import com.tajorgensen.patienttrials.utils.PatientTrialTestUtils;
import com.tajorgensen.patienttrials.utils.TrialPatientListTestUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class TrialPatientListWebModelTransformerTest {

    @Test
    void testFromDomainNull() {
        TrialPatientListWebModel expected = new TrialPatientListWebModel();
        expected.setPatients(new ArrayList<>());

        TrialPatientListWebModel actual = TrialPatientListWebModelTransformer.fromDomain(null);

        TrialPatientListTestUtils.assertWebModelsEqual(expected, actual);
    }

    @Test
    void testFromDomainEmpty() {
        TrialPatientListWebModel expected = new TrialPatientListWebModel();
        expected.setPatients(new ArrayList<>());

        TrialPatientListWebModel actual = TrialPatientListWebModelTransformer.fromDomain(Collections.emptyList());

        TrialPatientListTestUtils.assertWebModelsEqual(expected, actual);
    }

    @Test
    void testFromDomain() {
        PatientTrial domain = PatientTrialTestUtils.createDomain();
        domain.setPatient(PatientTestUtils.createDomain());
        TrialPatientListWebModel expected = TrialPatientListTestUtils.createWebModel();

        TrialPatientListWebModel actual = TrialPatientListWebModelTransformer.fromDomain(List.of(domain));

        TrialPatientListTestUtils.assertWebModelsEqual(expected, actual);
    }

}