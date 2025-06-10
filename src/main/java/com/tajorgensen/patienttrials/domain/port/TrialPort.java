package com.tajorgensen.patienttrials.domain.port;

import com.tajorgensen.patienttrials.domain.model.Trial;

import java.util.List;

public interface TrialPort {

    Trial createTrial(Trial trial);

    Trial getTrialById(Long id);

    void deleteTrialById(Long id);

    Trial updateTrial(Trial trial);

    List<Trial> getAllTrials();
}
