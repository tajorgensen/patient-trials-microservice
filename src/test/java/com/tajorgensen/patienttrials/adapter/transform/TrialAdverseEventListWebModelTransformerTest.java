package com.tajorgensen.patienttrials.adapter.transform;

import com.tajorgensen.patienttrials.adapter.model.TrialAdverseEventListWebModel;
import com.tajorgensen.patienttrials.domain.model.AdverseEvent;
import com.tajorgensen.patienttrials.utils.AdverseEventTestUtils;
import com.tajorgensen.patienttrials.utils.TrialAdverseEventListTestUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class TrialAdverseEventListWebModelTransformerTest {

    @Test
    void testFromDomainNull() {
        TrialAdverseEventListWebModel expected = new TrialAdverseEventListWebModel();
        expected.setAdverseEvents(new ArrayList<>());

        TrialAdverseEventListWebModel actual = TrialAdverseEventListWebModelTransformer.fromDomain(null);

        TrialAdverseEventListTestUtils.assertWebModelsEqual(expected, actual);
    }

    @Test
    void testFromDomainEmpty() {
        TrialAdverseEventListWebModel expected = new TrialAdverseEventListWebModel();
        expected.setAdverseEvents(new ArrayList<>());

        TrialAdverseEventListWebModel actual = TrialAdverseEventListWebModelTransformer.fromDomain(Collections.emptyList());

        TrialAdverseEventListTestUtils.assertWebModelsEqual(expected, actual);
    }

    @Test
    void testFromDomain() {
        AdverseEvent domain = AdverseEventTestUtils.createDomain();
        TrialAdverseEventListWebModel expected = TrialAdverseEventListTestUtils.createWebModel();

        TrialAdverseEventListWebModel actual = TrialAdverseEventListWebModelTransformer.fromDomain(List.of(domain));

        TrialAdverseEventListTestUtils.assertWebModelsEqual(expected, actual);
    }

}