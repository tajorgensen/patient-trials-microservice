package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.domain.port.TrialPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DeleteTrialUseCase {

    private TrialPort trialPort;

    public void execute(Long id) {
        trialPort.deleteTrialById(id);
    }

}
