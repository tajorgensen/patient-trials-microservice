package com.tajorgensen.patienttrials.domain.model;

import com.tajorgensen.patienttrials.common.constants.ApplicationConstants.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    private Long id;
    private String name;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String medicalHistory;
}
