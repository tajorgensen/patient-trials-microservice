package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.domain.model.AdverseEvent;
import com.tajorgensen.patienttrials.domain.port.AdverseEventPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CreateAdverseEventUseCase {

    private AdverseEventPort adverseEventPort;

    public AdverseEvent execute(AdverseEvent adverseEvent) {
        return adverseEventPort.createAdverseEvent(adverseEvent);
    }

}
