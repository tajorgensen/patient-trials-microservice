package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.domain.port.AdverseEventPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DeleteAdverseEventUseCase {

    private AdverseEventPort adverseEventPort;

    public void execute(Long id) {
        adverseEventPort.deleteAdverseEventById(id);
    }

}
