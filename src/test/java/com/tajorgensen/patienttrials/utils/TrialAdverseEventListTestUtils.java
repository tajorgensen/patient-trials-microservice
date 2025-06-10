package com.tajorgensen.patienttrials.utils;

import com.tajorgensen.patienttrials.adapter.model.AdverseEventWebModel;
import com.tajorgensen.patienttrials.adapter.model.TrialAdverseEventListWebModel;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;

public class TrialAdverseEventListTestUtils {

    public static void assertWebModelsEqual(TrialAdverseEventListWebModel expected, TrialAdverseEventListWebModel actual) {
        if (expected == null) {
            assertNull(actual);
            return;
        }

        AdverseEventTestUtils.assertWebModelListsAreEqual(expected.getAdverseEvents(), actual.getAdverseEvents());
    }

    public static TrialAdverseEventListWebModel createWebModel() {
        AdverseEventWebModel adverseEventWebModel = AdverseEventTestUtils.createWebModel();

        return TrialAdverseEventListWebModel.builder()
                .adverseEvents(List.of(adverseEventWebModel))
                .build();
    }

}
