package com.tajorgensen.patienttrials.adapter.transform;

import com.tajorgensen.patienttrials.adapter.model.PatientWebModel;
import com.tajorgensen.patienttrials.domain.model.Patient;

public class PatientWebModelTransformer {

    public static PatientWebModel fromDomain(Patient domain) {
        if (domain == null) {
            return null;
        }

        return PatientWebModel.builder()
                .id(domain.getId())
                .name(domain.getName())
                .dateOfBirth(domain.getDateOfBirth())
                .gender(domain.getGender())
                .medicalHistory(domain.getMedicalHistory())
                .build();
    }

    public static Patient toDomain(PatientWebModel webModel) {
        if (webModel == null) {
            return null;
        }

        return Patient.builder()
                .id(webModel.getId())
                .name(webModel.getName())
                .dateOfBirth(webModel.getDateOfBirth())
                .gender(webModel.getGender())
                .medicalHistory(webModel.getMedicalHistory())
                .build();
    }

}
