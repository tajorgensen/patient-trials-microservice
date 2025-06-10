package com.tajorgensen.patienttrials.domain.model;

import com.tajorgensen.patienttrials.common.constants.ApplicationConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientTrial {

    private Long id;

    private Long patientId;

    private Long trialId;

    private LocalDate enrollmentDate;

    private ApplicationConstants.TreatmentStatus treatmentStatus;

    private Patient patient;

    private Trial trial;

    private List<Drug> drugs;

}
