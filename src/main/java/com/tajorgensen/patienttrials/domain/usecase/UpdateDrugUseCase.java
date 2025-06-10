package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.domain.model.Drug;
import com.tajorgensen.patienttrials.domain.port.DrugPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UpdateDrugUseCase {

    private DrugPort drugPort;

    public Drug execute(Drug drug) {
        return drugPort.updateDrug(drug);
    }
}
