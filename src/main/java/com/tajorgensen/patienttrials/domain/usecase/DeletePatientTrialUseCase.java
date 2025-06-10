package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.domain.port.PatientTrialPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DeletePatientTrialUseCase {

    private PatientTrialPort patientTrialPort;

    public void execute(Long id) {
        patientTrialPort.deletePatientTrialById(id);
    }

}
