package com.tajorgensen.patienttrials.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trial {

    private Long id;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private String protocolDescription;

}
