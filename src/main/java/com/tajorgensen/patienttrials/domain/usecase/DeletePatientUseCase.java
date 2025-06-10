package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.domain.port.PatientPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DeletePatientUseCase {

    private PatientPort patientPort;

    public void execute(Long id) {
        patientPort.deletePatientById(id);
    }

}
