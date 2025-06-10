package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.domain.model.Trial;
import com.tajorgensen.patienttrials.domain.port.TrialPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CreateTrialUseCase {

    private TrialPort trialPort;

    public Trial execute(Trial trial) {
        return trialPort.createTrial(trial);
    }

}
