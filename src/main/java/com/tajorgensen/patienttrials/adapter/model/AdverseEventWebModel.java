package com.tajorgensen.patienttrials.adapter.model;

import com.tajorgensen.patienttrials.common.constants.ApplicationConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdverseEventWebModel implements Serializable {

    private Long id;

    private Long patientId;

    private Long trialId;

    private String eventDescription;

    private ApplicationConstants.EventSeverity eventSeverity;

}
