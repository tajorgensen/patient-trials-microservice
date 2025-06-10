package com.tajorgensen.patienttrials.utils;

import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.AdverseEventEntity;
import com.tajorgensen.patienttrials.adapter.model.AdverseEventWebModel;
import com.tajorgensen.patienttrials.common.constants.ApplicationConstants;
import com.tajorgensen.patienttrials.domain.model.AdverseEvent;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AdverseEventTestUtils {

    public static final Long ID = 1234567L;
    public static final Long PATIENT_ID = 123L;
    public static final Long TRIAL_ID = 14L;
    private static final String EVENT_DESCRIPTION = "Vomiting";
    private static final ApplicationConstants.EventSeverity EVENT_SEVERITY = ApplicationConstants.EventSeverity.MILD;

    public static void assertWebModelsEqual(AdverseEventWebModel expected, AdverseEventWebModel actual) {
        if (expected == null) {
            assertNull(actual);
            return;
        }

        assertAll(
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getPatientId(), actual.getPatientId()),
                () -> assertEquals(expected.getTrialId(), actual.getTrialId()),
                () -> assertEquals(expected.getEventDescription(), actual.getEventDescription()),
                () -> assertEquals(expected.getEventSeverity(), actual.getEventSeverity())
        );
    }

    public static void assertWebModelListsAreEqual(List<AdverseEventWebModel> expectedList, List<AdverseEventWebModel> actualList) {
        if (expectedList == null) {
            assertNull(actualList);
            return;
        }

        assertNotNull(actualList);
        assertEquals(expectedList.size(), actualList.size());

        for (int i = 0; i < expectedList.size(); i++) {
            assertWebModelsEqual(expectedList.get(i), actualList.get(i));
        }
    }

    public static void assertDomainModelsEqual(AdverseEvent expected, AdverseEvent actual) {
        if (expected == null) {
            assertNull(actual);
            return;
        }

        assertAll(
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getPatientId(), actual.getPatientId()),
                () -> assertEquals(expected.getTrialId(), actual.getTrialId()),
                () -> assertEquals(expected.getEventDescription(), actual.getEventDescription()),
                () -> assertEquals(expected.getEventSeverity(), actual.getEventSeverity())
        );
    }

    public static void assertEntitiesEqual(AdverseEventEntity expected, AdverseEventEntity actual) {
        if (expected == null) {
            assertNull(actual);
            return;
        }

        assertAll(
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getPatientId(), actual.getPatientId()),
                () -> assertEquals(expected.getTrialId(), actual.getTrialId()),
                () -> assertEquals(expected.getEventDescription(), actual.getEventDescription()),
                () -> assertEquals(expected.getEventSeverity(), actual.getEventSeverity())
        );
    }

    public static AdverseEventWebModel createWebModel() {
        return AdverseEventWebModel.builder()
                .id(ID)
                .patientId(PATIENT_ID)
                .trialId(TRIAL_ID)
                .eventDescription(EVENT_DESCRIPTION)
                .eventSeverity(EVENT_SEVERITY)
                .build();
    }

    public static AdverseEvent createDomain() {
        return AdverseEvent.builder()
                .id(ID)
                .patientId(PATIENT_ID)
                .trialId(TRIAL_ID)
                .eventDescription(EVENT_DESCRIPTION)
                .eventSeverity(EVENT_SEVERITY)
                .build();
    }

    public static AdverseEventEntity createEntity() {
        return AdverseEventEntity.builder()
                .id(ID)
                .patientId(PATIENT_ID)
                .trialId(TRIAL_ID)
                .eventDescription(EVENT_DESCRIPTION)
                .eventSeverity(EVENT_SEVERITY.name())
                .build();
    }
}
