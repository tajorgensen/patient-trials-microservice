package com.tajorgensen.patienttrials.utils;

import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.TrialEntity;
import com.tajorgensen.patienttrials.adapter.model.TrialWebModel;
import com.tajorgensen.patienttrials.domain.model.Trial;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TrialTestUtils {

    public static final Long ID = 1234L;
    public static final String NAME = "Salk polio vaccine field trials";
    private static final LocalDate START_DATE = LocalDate.of(1954, 1, 15);
    private static final LocalDate END_DATE = LocalDate.of(1955, 1, 15);
    private static final String PROTOCOL_DESCRIPTION = "A large-scale field trial of the vaccine's effectiveness in preventing paralytic polio";

    public static void assertWebModelsEqual(TrialWebModel expected, TrialWebModel actual) {
        if (expected == null) {
            assertNull(actual);
            return;
        }

        assertAll(
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getName(), actual.getName()),
                () -> assertEquals(expected.getStartDate(), actual.getStartDate()),
                () -> assertEquals(expected.getEndDate(), actual.getEndDate()),
                () -> assertEquals(expected.getProtocolDescription(), actual.getProtocolDescription())
        );
    }

    public static void assertDomainModelsEqual(Trial expected, Trial actual) {
        if (expected == null) {
            assertNull(actual);
            return;
        }

        assertAll(
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getName(), actual.getName()),
                () -> assertEquals(expected.getStartDate(), actual.getStartDate()),
                () -> assertEquals(expected.getEndDate(), actual.getEndDate()),
                () -> assertEquals(expected.getProtocolDescription(), actual.getProtocolDescription())
        );
    }

    public static void assertEntitiesEqual(TrialEntity expected, TrialEntity actual) {
        if (expected == null) {
            assertNull(actual);
            return;
        }

        assertAll(
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getName(), actual.getName()),
                () -> assertEquals(expected.getStartDate(), actual.getStartDate()),
                () -> assertEquals(expected.getEndDate(), actual.getEndDate()),
                () -> assertEquals(expected.getProtocolDescription(), actual.getProtocolDescription())
        );
    }

    public static TrialWebModel createWebModel() {
        return TrialWebModel.builder()
                .id(ID)
                .name(NAME)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .protocolDescription(PROTOCOL_DESCRIPTION)
                .build();
    }

    public static Trial createDomain() {
        return Trial.builder()
                .id(ID)
                .name(NAME)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .protocolDescription(PROTOCOL_DESCRIPTION)
                .build();
    }

    public static TrialEntity createEntity() {
        return TrialEntity.builder()
                .id(ID)
                .name(NAME)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .protocolDescription(PROTOCOL_DESCRIPTION)
                .build();
    }
}
