package com.tajorgensen.patienttrials.adapter.transform;

import com.tajorgensen.patienttrials.adapter.model.AdverseEventWebModel;
import com.tajorgensen.patienttrials.domain.model.AdverseEvent;

public class AdverseEventWebModelTransformer {

    public static AdverseEventWebModel fromDomain(AdverseEvent domain) {
        if (domain == null) {
            return null;
        }

        return AdverseEventWebModel.builder()
                .id(domain.getId())
                .patientId(domain.getPatientId())
                .trialId(domain.getTrialId())
                .eventDescription(domain.getEventDescription())
                .eventSeverity(domain.getEventSeverity())
                .build();
    }

    public static AdverseEvent toDomain(AdverseEventWebModel webModel) {
        if (webModel == null) {
            return null;
        }

        return AdverseEvent.builder()
                .id(webModel.getId())
                .id(webModel.getId())
                .patientId(webModel.getPatientId())
                .trialId(webModel.getTrialId())
                .eventDescription(webModel.getEventDescription())
                .eventSeverity(webModel.getEventSeverity())
                .build();
    }

}
