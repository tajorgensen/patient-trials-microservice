package com.tajorgensen.patienttrials.adapter.transform;

import com.tajorgensen.patienttrials.adapter.model.TrialDrugAverageDosageListWebModel;
import com.tajorgensen.patienttrials.adapter.model.TrialDrugAverageDosageWebModel;
import com.tajorgensen.patienttrials.domain.model.AverageDrugDosageResult;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

public class TrialDrugAverageDosageListWebModelTransformer {

    public static TrialDrugAverageDosageListWebModel fromDomain(List<AverageDrugDosageResult> domain) {
        if (CollectionUtils.isEmpty(domain)) {
            return TrialDrugAverageDosageListWebModel.builder().trialDrugs(Collections.emptyList()).build();
        }

        List<TrialDrugAverageDosageWebModel> trialDrugAverageDosageWebModelList = domain.stream().map(TrialDrugAverageDosageWebModelTransformer::fromDomain).toList();

        return TrialDrugAverageDosageListWebModel.builder()
                .trialDrugs(trialDrugAverageDosageWebModelList)
                .build();
    }
}
