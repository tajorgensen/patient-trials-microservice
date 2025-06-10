package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.domain.model.AdverseEvent;
import com.tajorgensen.patienttrials.domain.port.AdverseEventPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class GetAdverseEventsForTrialUseCase {

    private AdverseEventPort adverseEventPort;

    public List<AdverseEvent> execute(Long trialId) {
        return adverseEventPort.getAdverseEventsByTrialId(trialId);
    }

}
