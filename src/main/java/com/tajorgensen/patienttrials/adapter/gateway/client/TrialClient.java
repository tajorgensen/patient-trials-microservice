package com.tajorgensen.patienttrials.adapter.gateway.client;

import com.tajorgensen.patienttrials.adapter.gateway.repository.TrialRepository;
import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.TrialEntity;
import com.tajorgensen.patienttrials.common.exception.ErrorConstants;
import com.tajorgensen.patienttrials.common.exception.ResourceNotFoundException;
import com.tajorgensen.patienttrials.domain.model.Trial;
import com.tajorgensen.patienttrials.domain.port.TrialPort;
import lombok.AllArgsConstructor;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class TrialClient implements TrialPort {

    private TrialRepository trialRepository;

    @Override
    public Trial createTrial(Trial trial) {
        TrialEntity trialEntity = trialRepository.save(TrialEntity.of(trial));
        return trialEntity.toDomain();
    }

    @Override
    public Trial getTrialById(Long id) {
        Optional<TrialEntity> trialEntity = trialRepository.findById(id);

        if (trialEntity.isEmpty()) {
            throw new ResourceNotFoundException(ErrorConstants.TrialErrorCode.GET_ID_NOT_FOUND.getCode(), String.format("Unable to find trial with id of %d", id));
        }

        return trialEntity.get().toDomain();
    }

    @Override
    public void deleteTrialById(Long id) {
        trialRepository.deleteById(id);
    }

    @Override
    public Trial updateTrial(Trial trial) {
        try {
            TrialEntity trialEntity = trialRepository.saveIfExists(TrialEntity.of(trial));
            return trialEntity.toDomain();
        } catch (JpaObjectRetrievalFailureException ex) {
            throw new ResourceNotFoundException(ErrorConstants.TrialErrorCode.UPDATE_ID_NOT_FOUND.getCode(), String.format("Unable to find trial with id of %d", trial.getId()));
        }
    }

    @Override
    public List<Trial> getAllTrials() {
        List<TrialEntity> trialEntityList = trialRepository.findAll();

        if (CollectionUtils.isEmpty(trialEntityList)) {
            return Collections.emptyList();
        }

        return trialEntityList.stream().map(TrialEntity::toDomain).toList();
    }
}
