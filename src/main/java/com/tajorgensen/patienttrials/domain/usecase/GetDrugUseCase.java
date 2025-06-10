package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.domain.model.Drug;
import com.tajorgensen.patienttrials.domain.port.DrugPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetDrugUseCase {

    private DrugPort drugPort;

    public Drug execute(Long id) {
        return drugPort.getDrugById(id);
    }

}
