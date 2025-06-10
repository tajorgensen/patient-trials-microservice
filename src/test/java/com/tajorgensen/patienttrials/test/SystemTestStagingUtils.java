package com.tajorgensen.patienttrials.test;

import com.tajorgensen.patienttrials.adapter.model.DrugWebModel;
import com.tajorgensen.patienttrials.adapter.model.PatientTrialWebModel;
import com.tajorgensen.patienttrials.adapter.model.PatientWebModel;
import com.tajorgensen.patienttrials.adapter.model.TrialWebModel;
import com.tajorgensen.patienttrials.utils.DrugTestUtils;
import com.tajorgensen.patienttrials.utils.PatientTestUtils;
import com.tajorgensen.patienttrials.utils.PatientTrialTestUtils;
import com.tajorgensen.patienttrials.utils.TrialTestUtils;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

public class SystemTestStagingUtils {

    public static Long stagePatient(TestRestTemplate restTemplate) {
        PatientWebModel patientWebModel = PatientTestUtils.createWebModel();
        patientWebModel.setId(null);

        ResponseEntity<PatientWebModel> response = restTemplate.postForEntity("/patients", patientWebModel, PatientWebModel.class);

        return response.getBody().getId();
    }

    public static Long stageTrial(TestRestTemplate restTemplate) {
        TrialWebModel trialWebModel = TrialTestUtils.createWebModel();
        trialWebModel.setId(null);

        ResponseEntity<TrialWebModel> response = restTemplate.postForEntity("/trials", trialWebModel, TrialWebModel.class);

        return response.getBody().getId();
    }

    public static DrugWebModel stageDrug(TestRestTemplate restTemplate) {
        DrugWebModel drugWebModel = DrugTestUtils.createWebModel();
        drugWebModel.setId(null);

        ResponseEntity<DrugWebModel> response = restTemplate.postForEntity("/drugs", drugWebModel, DrugWebModel.class);

        return response.getBody();
    }

    public static PatientTrialWebModel stagePatientTrial(TestRestTemplate restTemplate, Long patientId, Long trialId) {
        PatientTrialWebModel patientTrialWebModel = PatientTrialTestUtils.createWebModel();
        patientTrialWebModel.setId(null);
        patientTrialWebModel.setDrugs(null);
        patientTrialWebModel.setPatientId(patientId);
        patientTrialWebModel.setTrialId(trialId);

        ResponseEntity<PatientTrialWebModel> response = restTemplate.postForEntity("/patientTrials", patientTrialWebModel, PatientTrialWebModel.class);

        return response.getBody();
    }
}
