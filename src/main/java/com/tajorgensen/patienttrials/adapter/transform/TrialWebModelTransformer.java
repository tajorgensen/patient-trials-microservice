package com.tajorgensen.patienttrials.adapter.transform;

import com.tajorgensen.patienttrials.adapter.model.TrialWebModel;
import com.tajorgensen.patienttrials.domain.model.Trial;

public class TrialWebModelTransformer {

    public static TrialWebModel fromDomain(Trial domain) {
        if (domain == null) {
            return null;
        }

        return TrialWebModel.builder()
                .id(domain.getId())
                .name(domain.getName())
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .protocolDescription(domain.getProtocolDescription())
                .build();
    }

    public static Trial toDomain(TrialWebModel webModel) {
        if (webModel == null) {
            return null;
        }

        return Trial.builder()
                .id(webModel.getId())
                .name(webModel.getName())
                .startDate(webModel.getStartDate())
                .endDate(webModel.getEndDate())
                .protocolDescription(webModel.getProtocolDescription())
                .build();
    }

}
