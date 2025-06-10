package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.domain.model.Trial;
import com.tajorgensen.patienttrials.domain.port.TrialPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class GetAllTrialsUseCase {

    private TrialPort trialPort;

    public List<Trial> execute() {
        return trialPort.getAllTrials();
    }

}
