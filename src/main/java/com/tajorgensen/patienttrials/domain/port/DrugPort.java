package com.tajorgensen.patienttrials.domain.port;

import com.tajorgensen.patienttrials.domain.model.Drug;

public interface DrugPort {

    Drug createDrug(Drug drug);

    Drug getDrugById(Long id);

    void deleteDrugById(Long id);

    Drug updateDrug(Drug drug);
}
