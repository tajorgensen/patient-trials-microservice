package com.tajorgensen.patienttrials.adapter.gateway.client;

import com.tajorgensen.patienttrials.adapter.gateway.repository.CalculateAverageDosageRepository;
import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.AverageDrugDosageResultEntity;
import com.tajorgensen.patienttrials.domain.model.AverageDrugDosageResult;
import com.tajorgensen.patienttrials.domain.port.GetAverageDosagePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
public class CalculateAverageDosageClient implements GetAverageDosagePort {

    private CalculateAverageDosageRepository calculateAverageDosageRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AverageDrugDosageResult> getAverageDosageForTrialId(Long trialId) {
        List<AverageDrugDosageResultEntity> averageDrugDosageResultList = calculateAverageDosageRepository.calculateAverageDosage(trialId.intValue());

        if (CollectionUtils.isEmpty(averageDrugDosageResultList)) {
            return Collections.emptyList();
        }

        return averageDrugDosageResultList.stream().map(AverageDrugDosageResultEntity::toDomain).toList();
    }

}
