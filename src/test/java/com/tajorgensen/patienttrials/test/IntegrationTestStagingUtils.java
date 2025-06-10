package com.tajorgensen.patienttrials.test;

import com.tajorgensen.patienttrials.adapter.gateway.repository.DrugRepository;
import com.tajorgensen.patienttrials.adapter.gateway.repository.PatientRepository;
import com.tajorgensen.patienttrials.adapter.gateway.repository.PatientTrialRepository;
import com.tajorgensen.patienttrials.adapter.gateway.repository.TrialRepository;
import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.DrugEntity;
import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.PatientEntity;
import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.PatientTrialEntity;
import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.TrialEntity;
import com.tajorgensen.patienttrials.utils.DrugTestUtils;
import com.tajorgensen.patienttrials.utils.PatientTestUtils;
import com.tajorgensen.patienttrials.utils.PatientTrialTestUtils;
import com.tajorgensen.patienttrials.utils.TrialTestUtils;

public class IntegrationTestStagingUtils {

    public static Long stagePatient(PatientRepository repository) {
        PatientEntity entity = PatientTestUtils.createEntity();
        entity.setId(null);

        PatientEntity result = repository.save(entity);
        return result.getId();
    }

    public static Long stageTrial(TrialRepository repository) {
        TrialEntity entity = TrialTestUtils.createEntity();
        entity.setId(null);

        TrialEntity result = repository.save(entity);
        return result.getId();
    }

    public static Long stageDrug(DrugRepository repository) {
        DrugEntity entity = DrugTestUtils.createEntity();
        entity.setId(null);

        DrugEntity result = repository.save(entity);
        return result.getId();
    }

    public static Long stagePatientTrial(PatientTrialRepository patientTrialRepository, PatientRepository patientRepository, TrialRepository trialRepository) {
        Long patientId = stagePatient(patientRepository);
        Long trialId = stageTrial(trialRepository);

        PatientTrialEntity entity = PatientTrialTestUtils.createEntity();
        entity.setId(null);
        entity.setPatientId(patientId);
        entity.setTrialId(trialId);

        PatientTrialEntity result = patientTrialRepository.save(entity);
        return result.getId();
    }

}
