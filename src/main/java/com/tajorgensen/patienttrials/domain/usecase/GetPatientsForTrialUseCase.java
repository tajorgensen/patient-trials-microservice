package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.domain.model.PatientTrial;
import com.tajorgensen.patienttrials.domain.port.PatientTrialPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class GetPatientsForTrialUseCase {

    private PatientTrialPort patientTrialPort;

    public List<PatientTrial> execute(Long trialId) {
        return patientTrialPort.getAllPatientsByTrialId(trialId);
    }
}
