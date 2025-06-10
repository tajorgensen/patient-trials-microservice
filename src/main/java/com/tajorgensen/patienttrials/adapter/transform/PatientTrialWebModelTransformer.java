package com.tajorgensen.patienttrials.adapter.transform;

import com.tajorgensen.patienttrials.adapter.model.PatientTrialWebModel;
import com.tajorgensen.patienttrials.domain.model.PatientTrial;

import java.util.Optional;

public class PatientTrialWebModelTransformer {

    public static PatientTrialWebModel fromDomain(PatientTrial domain) {
        if (domain == null) {
            return null;
        }

        return PatientTrialWebModel.builder()
                .id(domain.getId())
                .patientId(domain.getPatientId())
                .trialId(domain.getTrialId())
                .enrollmentDate(domain.getEnrollmentDate())
                .treatmentStatus(domain.getTreatmentStatus())
                .drugs(Optional.ofNullable(domain.getDrugs()).map(drugs -> drugs.stream().map(DrugWebModelTransformer::fromDomain).toList()).orElse(null))
                .build();
    }

    public static PatientTrial toDomain(PatientTrialWebModel webModel) {
        if (webModel == null) {
            return null;
        }

        return PatientTrial.builder()
                .id(webModel.getId())
                .patientId(webModel.getPatientId())
                .trialId(webModel.getTrialId())
                .enrollmentDate(webModel.getEnrollmentDate())
                .treatmentStatus(webModel.getTreatmentStatus())
                .drugs(Optional.ofNullable(webModel.getDrugs()).map(drugs -> drugs.stream().map(DrugWebModelTransformer::toDomain).toList()).orElse(null))
                .build();
    }

}
