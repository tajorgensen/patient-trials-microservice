package com.tajorgensen.patienttrials.adapter.transform;

import com.tajorgensen.patienttrials.adapter.model.PatientTrialStatusWebModel;
import com.tajorgensen.patienttrials.adapter.model.TrialPatientListWebModel;
import com.tajorgensen.patienttrials.domain.model.PatientTrial;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

public class TrialPatientListWebModelTransformer {

    public static TrialPatientListWebModel fromDomain(List<PatientTrial> domainList) {
        if (CollectionUtils.isEmpty(domainList)) {
            return TrialPatientListWebModel.builder().patients(Collections.emptyList()).build();
        }

        List<PatientTrialStatusWebModel> patientTrialStatusList = domainList.stream().map(PatientTrialStatusWebModelTransformer::fromDomain).toList();

        return TrialPatientListWebModel.builder()
                .patients(patientTrialStatusList)
                .build();
    }

}
