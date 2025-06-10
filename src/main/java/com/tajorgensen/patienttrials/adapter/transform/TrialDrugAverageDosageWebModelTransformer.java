package com.tajorgensen.patienttrials.adapter.transform;

import com.tajorgensen.patienttrials.adapter.model.TrialDrugAverageDosageWebModel;
import com.tajorgensen.patienttrials.domain.model.AverageDrugDosageResult;

public class TrialDrugAverageDosageWebModelTransformer {

    public static TrialDrugAverageDosageWebModel fromDomain(AverageDrugDosageResult domain) {
        if (domain == null) {
            return null;
        }

        return TrialDrugAverageDosageWebModel.builder()
                .drugId(domain.getDrugId())
                .drugName(domain.getDrugName())
                .trialId(domain.getTrialId())
                .trialName(domain.getTrialName())
                .averageDosage(domain.getAverageDosage())
                .dosageMeasurementType(domain.getDosageMeasurementType())
                .patientCount(domain.getPatientCount())
                .build();
    }
}
