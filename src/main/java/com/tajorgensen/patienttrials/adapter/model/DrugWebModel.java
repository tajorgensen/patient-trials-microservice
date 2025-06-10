package com.tajorgensen.patienttrials.adapter.model;

import com.tajorgensen.patienttrials.common.constants.ApplicationConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DrugWebModel implements Serializable {

    private Long id;

    private String name;

    private String chemicalFormula;

    private String manufacturer;

    private BigDecimal dosage;

    private ApplicationConstants.UnitsOfMeasurement dosageMeasurementType;

}
