package com.tajorgensen.patienttrials.adapter.gateway.client;

import com.tajorgensen.patienttrials.adapter.gateway.repository.AdverseEventRepository;
import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.AdverseEventEntity;
import com.tajorgensen.patienttrials.common.exception.ErrorConstants;
import com.tajorgensen.patienttrials.common.exception.ResourceNotFoundException;
import com.tajorgensen.patienttrials.domain.model.AdverseEvent;
import com.tajorgensen.patienttrials.domain.port.AdverseEventPort;
import lombok.AllArgsConstructor;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class AdverseEventClient implements AdverseEventPort {

    private AdverseEventRepository adverseEventRepository;

    @Override
    public AdverseEvent createAdverseEvent(AdverseEvent adverseEvent) {
        AdverseEventEntity adverseEventEntity = adverseEventRepository.save(AdverseEventEntity.of(adverseEvent));
        return adverseEventEntity.toDomain();
    }

    @Override
    public AdverseEvent getAdverseEventById(Long id) {
        Optional<AdverseEventEntity> adverseEventEntity = adverseEventRepository.findById(id);

        if (adverseEventEntity.isEmpty()) {
            throw new ResourceNotFoundException(ErrorConstants.AdverseEventErrorCode.GET_ID_NOT_FOUND.getCode(), String.format("Unable to find adverse event with id of %d", id));
        }

        return adverseEventEntity.get().toDomain();
    }

    @Override
    public List<AdverseEvent> getAdverseEventsByTrialId(Long trialId) {
        List<AdverseEventEntity> adverseEventEntityList = adverseEventRepository.findByTrialId(trialId);

        if (CollectionUtils.isEmpty(adverseEventEntityList)) {
            return Collections.emptyList();
        }

        return adverseEventEntityList.stream().map(AdverseEventEntity::toDomain).toList();
    }

    @Override
    public void deleteAdverseEventById(Long id) {
        adverseEventRepository.deleteById(id);
    }

    @Override
    public AdverseEvent updateAdverseEvent(AdverseEvent adverseEvent) {
        try {
            AdverseEventEntity adverseEventEntity = adverseEventRepository.saveIfExists(AdverseEventEntity.of(adverseEvent));
            return adverseEventEntity.toDomain();
        } catch (JpaObjectRetrievalFailureException ex) {
            throw new ResourceNotFoundException(ErrorConstants.AdverseEventErrorCode.UPDATE_ID_NOT_FOUND.getCode(), String.format("Unable to find adverse event with id of %d", adverseEvent.getId()));
        }
    }
}
