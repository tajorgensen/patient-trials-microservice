package com.tajorgensen.patienttrials.domain.usecase;

import com.tajorgensen.patienttrials.domain.model.AverageDrugDosageResult;
import com.tajorgensen.patienttrials.domain.port.GetAverageDosagePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class GetAverageDrugDosageForTrialUseCase {

    private GetAverageDosagePort getAverageDosagePort;

    public List<AverageDrugDosageResult> execute(Long trialId) {
        return getAverageDosagePort.getAverageDosageForTrialId(trialId);
    }
}
