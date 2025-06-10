package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.domain.model.PatientTrial;
import com.tajorgensen.patienttrials.domain.port.PatientTrialPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetPatientTrialUseCase {

    private PatientTrialPort patientTrialPort;

    public PatientTrial execute(Long id) {
        return patientTrialPort.getPatientTrialById(id);
    }

}
