package com.tajorgensen.patienttrials.adapter.gateway.client;

import com.tajorgensen.patienttrials.adapter.gateway.repository.DrugRepository;
import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.DrugEntity;
import com.tajorgensen.patienttrials.common.exception.ErrorConstants;
import com.tajorgensen.patienttrials.common.exception.ResourceNotFoundException;
import com.tajorgensen.patienttrials.domain.model.Drug;
import com.tajorgensen.patienttrials.domain.port.DrugPort;
import lombok.AllArgsConstructor;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class DrugClient implements DrugPort {

    private DrugRepository drugRepository;

    @Override
    public Drug createDrug(Drug drug) {
        DrugEntity drugEntity = drugRepository.save(DrugEntity.of(drug));
        return drugEntity.toDomain();
    }

    @Override
    public Drug getDrugById(Long id) {
        Optional<DrugEntity> drugEntity = drugRepository.findById(id);

        if (drugEntity.isEmpty()) {
            throw new ResourceNotFoundException(ErrorConstants.DrugErrorCode.GET_ID_NOT_FOUND.getCode(), String.format("Unable to find drug with id of %d", id));
        }

        return drugEntity.get().toDomain();
    }

    @Override
    public void deleteDrugById(Long id) {
        drugRepository.deleteById(id);
    }

    @Override
    public Drug updateDrug(Drug drug) {
        try {
            DrugEntity drugEntity = drugRepository.saveIfExists(DrugEntity.of(drug));
            return drugEntity.toDomain();
        } catch (JpaObjectRetrievalFailureException ex) {
            throw new ResourceNotFoundException(ErrorConstants.DrugErrorCode.UPDATE_ID_NOT_FOUND.getCode(), String.format("Unable to find drug with id of %d", drug.getId()));
        }
    }
}
