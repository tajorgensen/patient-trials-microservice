package com.tajorgensen.patienttrials.domain.model;

import com.tajorgensen.patienttrials.common.constants.ApplicationConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdverseEvent {

    private Long id;

    private Long patientId;

    private Long trialId;

    private String eventDescription;

    private ApplicationConstants.EventSeverity eventSeverity;

    private Patient patient;

    private Trial trial;

}
