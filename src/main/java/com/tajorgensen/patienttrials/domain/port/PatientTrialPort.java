package com.tajorgensen.patienttrials.domain.port;

import com.tajorgensen.patienttrials.domain.model.PatientTrial;

import java.util.List;

public interface PatientTrialPort {

    PatientTrial createPatientTrial(PatientTrial patientTrial);

    PatientTrial getPatientTrialById(Long id);

    void deletePatientTrialById(Long id);

    PatientTrial updatePatientTrial(PatientTrial patient);

    List<PatientTrial> getAllPatientsByTrialId(Long trialId);
}
