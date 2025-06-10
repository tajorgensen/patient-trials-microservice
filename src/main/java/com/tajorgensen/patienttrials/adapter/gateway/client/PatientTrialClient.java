package com.tajorgensen.patienttrials.adapter.gateway.client;

import com.tajorgensen.patienttrials.adapter.gateway.repository.PatientTrialDrugRepository;
import com.tajorgensen.patienttrials.adapter.gateway.repository.PatientTrialRepository;
import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.PatientTrialDrugEntity;
import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.PatientTrialEntity;
import com.tajorgensen.patienttrials.common.exception.ErrorConstants;
import com.tajorgensen.patienttrials.common.exception.ResourceNotFoundException;
import com.tajorgensen.patienttrials.domain.model.Drug;
import com.tajorgensen.patienttrials.domain.model.PatientTrial;
import com.tajorgensen.patienttrials.domain.port.PatientTrialPort;
import lombok.AllArgsConstructor;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class PatientTrialClient implements PatientTrialPort {

    private PatientTrialRepository patientTrialRepository;

    private PatientTrialDrugRepository patientTrialDrugRepository;

    @Override
    public PatientTrial createPatientTrial(PatientTrial patientTrial) {
        PatientTrialEntity patientTrialEntity = patientTrialRepository.save(PatientTrialEntity.of(patientTrial));

        PatientTrial result = patientTrialEntity.toDomain();

        if (!CollectionUtils.isEmpty(patientTrial.getDrugs())) {
            List<Drug> patientTrialDrugs = patientTrial.getDrugs().stream().map(drug -> PatientTrialDrugEntity.of(drug, patientTrialEntity.getId())).map(ptDrug -> {
                PatientTrialDrugEntity savedDrug = patientTrialDrugRepository.save(ptDrug);

                return savedDrug.toDomain();
            }).toList();

            result.setDrugs(patientTrialDrugs);
        }

        return result;
    }

    @Override
    public PatientTrial getPatientTrialById(Long id) {
        Optional<PatientTrialEntity> patientTrialEntity = patientTrialRepository.findById(id);

        if (patientTrialEntity.isEmpty()) {
            throw new ResourceNotFoundException(ErrorConstants.PatientTrialErrorCode.GET_ID_NOT_FOUND.getCode(), String.format("Unable to find patientTrial with id of %d", id));
        }

        PatientTrial result = patientTrialEntity.get().toDomain();

        List<PatientTrialDrugEntity> patientTrialDrugEntities = patientTrialDrugRepository.findAllByPatientTrialId(result.getId());

        if (!CollectionUtils.isEmpty(patientTrialDrugEntities)) {
            List<Drug> patientTrialDrugs = patientTrialDrugEntities.stream().map(drug -> drug.toDomain()).toList();

            result.setDrugs(patientTrialDrugs);
        }

        return result;
    }

    @Override
    public void deletePatientTrialById(Long id) {
        patientTrialDrugRepository.deleteAllByPatientTrialId(id);
        patientTrialRepository.deleteById(id);


    }

    @Override
    public PatientTrial updatePatientTrial(PatientTrial patientTrial) {
        PatientTrialEntity patientTrialEntity = null;
        try {
            patientTrialEntity = patientTrialRepository.saveIfExists(PatientTrialEntity.of(patientTrial));
        } catch (JpaObjectRetrievalFailureException ex) {
            throw new ResourceNotFoundException(ErrorConstants.PatientTrialErrorCode.UPDATE_ID_NOT_FOUND.getCode(), String.format("Unable to find patientTrial with id of %d", patientTrial.getId()));
        }

        return patientTrialEntity.toDomain();
    }

    @Override
    public List<PatientTrial> getAllPatientsByTrialId(Long trialId) {
        List<PatientTrialEntity> patientTrialEntityList = patientTrialRepository.findByTrialIdWithEagerRelationships(trialId);

        if (CollectionUtils.isEmpty(patientTrialEntityList)) {
            return Collections.emptyList();
        }

        return patientTrialEntityList.stream().map(PatientTrialEntity::toDomain).toList();
    }
}
