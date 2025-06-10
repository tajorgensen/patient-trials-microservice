package com.tajorgensen.patienttrials.adapter.transform;

import com.tajorgensen.patienttrials.adapter.model.PatientTrialStatusWebModel;
import com.tajorgensen.patienttrials.domain.model.PatientTrial;

import java.util.Optional;

public class PatientTrialStatusWebModelTransformer {

    public static PatientTrialStatusWebModel fromDomain(PatientTrial domain) {
        if (domain == null) {
            return null;
        }

        return PatientTrialStatusWebModel.builder()
                .patient(PatientWebModelTransformer.fromDomain(domain.getPatient()))
                .patientTrialId(domain.getId())
                .enrollmentDate(domain.getEnrollmentDate())
                .treatmentStatus(domain.getTreatmentStatus())
                .drugs(Optional.ofNullable(domain.getDrugs()).map(drugs -> drugs.stream().map(DrugWebModelTransformer::fromDomain).toList()).orElse(null))
                .build();
    }
}
