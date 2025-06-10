package com.tajorgensen.patienttrials.domain.port;

import com.tajorgensen.patienttrials.domain.model.AverageDrugDosageResult;

import java.util.List;

public interface GetAverageDosagePort {

    List<AverageDrugDosageResult> getAverageDosageForTrialId(Long trialId);
}
