package com.tajorgensen.patienttrials.adapter.api;

import com.tajorgensen.patienttrials.adapter.gateway.repository.DrugRepository;
import com.tajorgensen.patienttrials.adapter.gateway.repository.PatientRepository;
import com.tajorgensen.patienttrials.adapter.gateway.repository.PatientTrialDrugRepository;
import com.tajorgensen.patienttrials.adapter.gateway.repository.PatientTrialRepository;
import com.tajorgensen.patienttrials.adapter.gateway.repository.TrialRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseControllerTest {

    @Autowired
    private PatientTrialDrugRepository repository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private TrialRepository trialRepository;

    @Autowired
    private PatientTrialRepository patientTrialRepository;

    @Autowired
    private DrugRepository drugRepository;

    @AfterEach
    public void cleanUp() {
        repository.deleteAll();
        patientTrialRepository.deleteAll();
        patientRepository.deleteAll();
        trialRepository.deleteAll();
        drugRepository.deleteAll();
    }
}
