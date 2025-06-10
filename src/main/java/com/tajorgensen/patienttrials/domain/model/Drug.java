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
public class Drug {

    private Long id;

    private String name;

    private String chemicalFormula;

    private String manufacturer;

    private BigDecimal dosage;

    private ApplicationConstants.UnitsOfMeasurement dosageMeasurementType;

}
