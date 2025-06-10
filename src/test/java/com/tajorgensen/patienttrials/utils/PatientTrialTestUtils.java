package com.tajorgensen.patienttrials.utils;

import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.PatientTrialEntity;
import com.tajorgensen.patienttrials.adapter.model.PatientTrialWebModel;
import com.tajorgensen.patienttrials.common.constants.ApplicationConstants;
import com.tajorgensen.patienttrials.domain.model.PatientTrial;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PatientTrialTestUtils {

    public static final Long ID = 1234L;
    private static final Long PATIENT_ID = 123L;
    private static final Long TRIAL_ID = 14L;
    private static final ApplicationConstants.TreatmentStatus TREATMENT_STATUS = ApplicationConstants.TreatmentStatus.ACTIVE;
    private static final LocalDate ENROLLMENT_DATE = LocalDate.now().minusMonths(6);

    public static void assertWebModelsEqual(PatientTrialWebModel expected, PatientTrialWebModel actual) {
        if (expected == null) {
            assertNull(actual);
            return;
        }

        assertAll(
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getPatientId(), actual.getPatientId()),
                () -> assertEquals(expected.getTrialId(), actual.getTrialId()),
                () -> Assertions.assertEquals(expected.getTreatmentStatus(), actual.getTreatmentStatus()),
                () -> assertEquals(expected.getEnrollmentDate(), actual.getEnrollmentDate()),
                () -> DrugTestUtils.assertWebModelListsAreEqual(expected.getDrugs(), actual.getDrugs())
        );
    }

    public static void assertDomainModelsEqual(PatientTrial expected, PatientTrial actual) {
        if (expected == null) {
            assertNull(actual);
            return;
        }

        assertAll(
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getPatientId(), actual.getPatientId()),
                () -> assertEquals(expected.getTrialId(), actual.getTrialId()),
                () -> Assertions.assertEquals(expected.getTreatmentStatus(), actual.getTreatmentStatus()),
                () -> assertEquals(expected.getEnrollmentDate(), actual.getEnrollmentDate()),
                () -> DrugTestUtils.assertDomainModelListsAreEqual(expected.getDrugs(), actual.getDrugs()),
                () -> PatientTestUtils.assertDomainModelsEqual(expected.getPatient(), actual.getPatient())
        );
    }

    public static void assertEntitiesEqual(PatientTrialEntity expected, PatientTrialEntity actual) {
        if (expected == null) {
            assertNull(actual);
            return;
        }

        assertAll(
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getPatientId(), actual.getPatientId()),
                () -> assertEquals(expected.getTrialId(), actual.getTrialId()),
                () -> assertEquals(expected.getTreatmentStatus(), actual.getTreatmentStatus()),
                () -> assertEquals(expected.getEnrollmentDate(), actual.getEnrollmentDate())
        );
    }

    public static PatientTrialWebModel createWebModel() {
        return PatientTrialWebModel.builder()
                .id(ID)
                .patientId(PATIENT_ID)
                .trialId(TRIAL_ID)
                .treatmentStatus(TREATMENT_STATUS)
                .enrollmentDate(ENROLLMENT_DATE)
                .drugs(List.of(DrugTestUtils.createWebModel()))
                .build();
    }

    public static PatientTrial createDomain() {
        return PatientTrial.builder()
                .id(ID)
                .patientId(PATIENT_ID)
                .trialId(TRIAL_ID)
                .treatmentStatus(TREATMENT_STATUS)
                .enrollmentDate(ENROLLMENT_DATE)
                .drugs(List.of(DrugTestUtils.createDomain()))
                .build();
    }

    public static PatientTrial createEagerDomain() {
        PatientTrial patientTrial = createDomain();
        patientTrial.setPatient(PatientTestUtils.createDomain());
        patientTrial.setTrial(TrialTestUtils.createDomain());

        return patientTrial;
    }

    public static PatientTrialEntity createEntity() {
        return PatientTrialEntity.builder()
                .id(ID)
                .patientId(PATIENT_ID)
                .trialId(TRIAL_ID)
                .treatmentStatus(TREATMENT_STATUS.name())
                .enrollmentDate(ENROLLMENT_DATE)
                .build();
    }


}
