package com.tajorgensen.patienttrials.domain.model;

import com.tajorgensen.patienttrials.common.constants.ApplicationConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AverageDrugDosageResult {

    private Integer trialId;

    private String trialName;

    private Integer drugId;

    private String drugName;

    private ApplicationConstants.UnitsOfMeasurement dosageMeasurementType;

    private BigDecimal averageDosage;

    private Integer patientCount;

}
