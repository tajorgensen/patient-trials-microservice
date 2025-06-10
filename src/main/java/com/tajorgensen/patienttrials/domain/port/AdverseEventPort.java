package com.tajorgensen.patienttrials.domain.port;

import com.tajorgensen.patienttrials.domain.model.AdverseEvent;

import java.util.List;

public interface AdverseEventPort {

    AdverseEvent createAdverseEvent(AdverseEvent patient);

    AdverseEvent getAdverseEventById(Long id);

    List<AdverseEvent> getAdverseEventsByTrialId(Long trialId);

    void deleteAdverseEventById(Long id);

    AdverseEvent updateAdverseEvent(AdverseEvent patient);
}
