package com.tajorgensen.patienttrials.adapter.model;

import com.tajorgensen.patienttrials.common.constants.ApplicationConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientWebModel implements Serializable {

    private Long id;
    private String name;
    private ApplicationConstants.Gender gender;
    private LocalDate dateOfBirth;
    private String medicalHistory;

}
