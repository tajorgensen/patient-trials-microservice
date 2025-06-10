package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.domain.model.Patient;
import com.tajorgensen.patienttrials.domain.port.PatientPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetPatientUseCase {

    private PatientPort patientPort;

    public Patient execute(Long id) {
        return patientPort.getPatientById(id);
    }

}
