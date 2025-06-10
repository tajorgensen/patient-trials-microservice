package com.tajorgensen.patienttrials.domain.port;

import com.tajorgensen.patienttrials.domain.model.Patient;

public interface PatientPort {

    Patient createPatient(Patient patient);

    Patient getPatientById(Long id);

    void deletePatientById(Long id);

    Patient updatePatient(Patient patient);
}
