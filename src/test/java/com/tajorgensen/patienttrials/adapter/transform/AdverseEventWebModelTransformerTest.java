package com.tajorgensen.patienttrials.adapter.transform;

import com.tajorgensen.patienttrials.adapter.model.AdverseEventWebModel;
import com.tajorgensen.patienttrials.domain.model.AdverseEvent;
import com.tajorgensen.patienttrials.utils.AdverseEventTestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class AdverseEventWebModelTransformerTest {

    @Test
    void testFromDomainNull() {
        assertNull(AdverseEventWebModelTransformer.fromDomain(null));
    }

    @Test
    void testFromDomainEmpty() {
        AdverseEvent domain = new AdverseEvent();
        AdverseEventWebModel expected = new AdverseEventWebModel();

        AdverseEventWebModel actual = AdverseEventWebModelTransformer.fromDomain(domain);

        AdverseEventTestUtils.assertWebModelsEqual(expected, actual);
    }

    @Test
    void testFromDomain() {
        AdverseEvent domain = AdverseEventTestUtils.createDomain();
        AdverseEventWebModel expected = AdverseEventTestUtils.createWebModel();

        AdverseEventWebModel actual = AdverseEventWebModelTransformer.fromDomain(domain);

        AdverseEventTestUtils.assertWebModelsEqual(expected, actual);
    }

    @Test
    void testToDomainNull() {
        assertNull(AdverseEventWebModelTransformer.toDomain(null));
    }

    @Test
    void testToDomainEmpty() {
        AdverseEventWebModel webModel = new AdverseEventWebModel();
        AdverseEvent expected = new AdverseEvent();

        AdverseEvent actual = AdverseEventWebModelTransformer.toDomain(webModel);

        AdverseEventTestUtils.assertDomainModelsEqual(expected, actual);
    }

    @Test
    void testToDomain() {
        AdverseEventWebModel webModel = AdverseEventTestUtils.createWebModel();
        AdverseEvent expected = AdverseEventTestUtils.createDomain();

        AdverseEvent actual = AdverseEventWebModelTransformer.toDomain(webModel);

        AdverseEventTestUtils.assertDomainModelsEqual(expected, actual);
    }
}