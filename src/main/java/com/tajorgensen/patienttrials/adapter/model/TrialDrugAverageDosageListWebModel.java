package com.tajorgensen.patienttrials.adapter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrialDrugAverageDosageListWebModel implements Serializable {

    private List<TrialDrugAverageDosageWebModel> trialDrugs;

}
