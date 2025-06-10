package com.tajorgensen.patienttrials.adapter.transform;

import com.tajorgensen.patienttrials.adapter.model.AdverseEventWebModel;
import com.tajorgensen.patienttrials.adapter.model.TrialAdverseEventListWebModel;
import com.tajorgensen.patienttrials.domain.model.AdverseEvent;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

public class TrialAdverseEventListWebModelTransformer {

    public static TrialAdverseEventListWebModel fromDomain(List<AdverseEvent> domainList) {
        if (CollectionUtils.isEmpty(domainList)) {
            return TrialAdverseEventListWebModel.builder().adverseEvents(Collections.emptyList()).build();
        }

        List<AdverseEventWebModel> adverseEventsList = domainList.stream().map(AdverseEventWebModelTransformer::fromDomain).toList();

        return TrialAdverseEventListWebModel.builder()
                .adverseEvents(adverseEventsList)
                .build();
    }

}
