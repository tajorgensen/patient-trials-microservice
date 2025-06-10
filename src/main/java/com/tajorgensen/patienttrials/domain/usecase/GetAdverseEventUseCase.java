package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.domain.model.AdverseEvent;
import com.tajorgensen.patienttrials.domain.port.AdverseEventPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetAdverseEventUseCase {

    private AdverseEventPort adverseEventPort;

    public AdverseEvent execute(Long id) {
        return adverseEventPort.getAdverseEventById(id);
    }

}
