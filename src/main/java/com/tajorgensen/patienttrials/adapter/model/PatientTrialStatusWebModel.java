package com.tajorgensen.patienttrials.adapter.model;

import com.tajorgensen.patienttrials.common.constants.ApplicationConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientTrialStatusWebModel implements Serializable {

    private PatientWebModel patient;

    private Long patientTrialId;

    private LocalDate enrollmentDate;

    private ApplicationConstants.TreatmentStatus treatmentStatus;

    private List<DrugWebModel> drugs;

}
