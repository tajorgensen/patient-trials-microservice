package com.tajorgensen.patienttrials.adapter.transform;

import com.tajorgensen.patienttrials.adapter.model.PatientTrialStatusWebModel;
import com.tajorgensen.patienttrials.domain.model.PatientTrial;
import com.tajorgensen.patienttrials.utils.PatientTestUtils;
import com.tajorgensen.patienttrials.utils.PatientTrialStatusTestUtils;
import com.tajorgensen.patienttrials.utils.PatientTrialTestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class PatientTrialStatusWebModelTransformerTest {

    @Test
    void testFromDomainNull() {
        assertNull(PatientTrialStatusWebModelTransformer.fromDomain(null));
    }

    @Test
    void testFromDomainEmpty() {
        PatientTrial domain = new PatientTrial();
        PatientTrialStatusWebModel expected = new PatientTrialStatusWebModel();

        PatientTrialStatusWebModel actual = PatientTrialStatusWebModelTransformer.fromDomain(domain);

        PatientTrialStatusTestUtils.assertWebModelsEqual(expected, actual);
    }

    @Test
    void testFromDomain() {
        PatientTrial domain = PatientTrialTestUtils.createDomain();
        domain.setPatient(PatientTestUtils.createDomain());
        PatientTrialStatusWebModel expected = PatientTrialStatusTestUtils.createWebModel();

        PatientTrialStatusWebModel actual = PatientTrialStatusWebModelTransformer.fromDomain(domain);

        PatientTrialStatusTestUtils.assertWebModelsEqual(expected, actual);
    }


}