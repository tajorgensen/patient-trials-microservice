package com.tajorgensen.patienttrials.adapter.transform;

import com.tajorgensen.patienttrials.adapter.model.TrialWebModel;
import com.tajorgensen.patienttrials.domain.model.Trial;
import com.tajorgensen.patienttrials.utils.TrialTestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class TrialWebModelTransformerTest {

    @Test
    void testFromDomainNull() {
        assertNull(TrialWebModelTransformer.fromDomain(null));
    }

    @Test
    void testFromDomainEmpty() {
        Trial domain = new Trial();
        TrialWebModel expected = new TrialWebModel();

        TrialWebModel actual = TrialWebModelTransformer.fromDomain(domain);

        TrialTestUtils.assertWebModelsEqual(expected, actual);
    }

    @Test
    void testFromDomain() {
        Trial domain = TrialTestUtils.createDomain();
        TrialWebModel expected = TrialTestUtils.createWebModel();

        TrialWebModel actual = TrialWebModelTransformer.fromDomain(domain);

        TrialTestUtils.assertWebModelsEqual(expected, actual);
    }

    @Test
    void testToDomainNull() {
        assertNull(TrialWebModelTransformer.toDomain(null));
    }

    @Test
    void testToDomainEmpty() {
        TrialWebModel webModel = new TrialWebModel();
        Trial expected = new Trial();

        Trial actual = TrialWebModelTransformer.toDomain(webModel);

        TrialTestUtils.assertDomainModelsEqual(expected, actual);
    }

    @Test
    void testToDomain() {
        TrialWebModel webModel = TrialTestUtils.createWebModel();
        Trial expected = TrialTestUtils.createDomain();

        Trial actual = TrialWebModelTransformer.toDomain(webModel);

        TrialTestUtils.assertDomainModelsEqual(expected, actual);
    }
}
