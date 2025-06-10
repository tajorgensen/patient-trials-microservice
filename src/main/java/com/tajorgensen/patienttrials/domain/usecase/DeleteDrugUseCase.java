package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.domain.port.DrugPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DeleteDrugUseCase {

    private DrugPort drugPort;

    public void execute(Long id) {
        drugPort.deleteDrugById(id);
    }

}
