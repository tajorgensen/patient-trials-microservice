package com.tajorgensen.patienttrials.utils;

import com.tajorgensen.patienttrials.adapter.model.DrugWebModel;
import com.tajorgensen.patienttrials.adapter.model.PatientTrialWebModel;
import com.tajorgensen.patienttrials.adapter.model.PatientWebModel;
import com.tajorgensen.patienttrials.adapter.model.TrialWebModel;
import com.tajorgensen.patienttrials.common.constants.ApplicationConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

public class EndToEndTestUtils {

    public static final String BASE_API_URL = "http://localhost:8080";

    public static final String PATIENTS_PATH = "/patients";
    public static final String PATIENT_TRIALS_PATH = "/patientTrials";
    public static final String DRUGS_PATH = "/drugs";
    public static final String TRIALS_PATH = "/trials";
    public static final String GET_PATH = "/{id}";
    public static final String DELETE_PATH = "/{id}";

    public static Long stagePatient(RestTemplate restTemplate) {
        PatientWebModel patientWebModel = PatientTestUtils.createWebModel();
        patientWebModel.setId(null);

        ResponseEntity<PatientWebModel> response = restTemplate.postForEntity(BASE_API_URL + PATIENTS_PATH, patientWebModel, PatientWebModel.class);

        return response.getBody().getId();
    }

    public static void deletePatient(RestTemplate restTemplate, Long id) {
        restTemplate.delete(BASE_API_URL + PATIENTS_PATH + DELETE_PATH, id);
    }

    public static PatientWebModel getPatient(RestTemplate restTemplate, Long id) {
        ResponseEntity<PatientWebModel> response = restTemplate.getForEntity(BASE_API_URL + PATIENTS_PATH + GET_PATH, PatientWebModel.class, id);

        return response.getBody();
    }

    public static TrialWebModel stageTrial(RestTemplate restTemplate) {
        TrialWebModel trailWebModel = TrialTestUtils.createWebModel();
        trailWebModel.setId(null);

        ResponseEntity<TrialWebModel> response = restTemplate.postForEntity(BASE_API_URL + TRIALS_PATH, trailWebModel, TrialWebModel.class);

        return response.getBody();
    }

    public static void deleteTrial(RestTemplate restTemplate, Long id) {
        restTemplate.delete(BASE_API_URL + TRIALS_PATH + DELETE_PATH, id);
    }

    public static TrialWebModel getTrial(RestTemplate restTemplate, Long id) {
        ResponseEntity<TrialWebModel> response = restTemplate.getForEntity(BASE_API_URL + TRIALS_PATH + GET_PATH, TrialWebModel.class, id);

        return response.getBody();
    }

    public static DrugWebModel stageDrug(RestTemplate restTemplate, String drugName) {
        DrugWebModel drugWebModel = DrugTestUtils.createWebModel();
        drugWebModel.setId(null);
        drugWebModel.setName(drugName);

        ResponseEntity<DrugWebModel> response = restTemplate.postForEntity(BASE_API_URL + DRUGS_PATH, drugWebModel, DrugWebModel.class);

        return response.getBody();
    }

    public static void deleteDrug(RestTemplate restTemplate, Long id) {
        restTemplate.delete(BASE_API_URL + DRUGS_PATH + DELETE_PATH, id);
    }

    public static DrugWebModel getDrug(RestTemplate restTemplate, Long id) {
        ResponseEntity<DrugWebModel> response = restTemplate.getForEntity(BASE_API_URL + DRUGS_PATH + GET_PATH, DrugWebModel.class, id);

        return response.getBody();
    }

    public static PatientTrialWebModel stagePatientTrial(RestTemplate restTemplate, Long patientId, Long trialId, List<DrugWebModel> drugs) {
        PatientTrialWebModel patientTrialWebModel = PatientTrialWebModel.builder()
                .enrollmentDate(LocalDate.now())
                .patientId(patientId)
                .trialId(trialId)
                .treatmentStatus(ApplicationConstants.TreatmentStatus.ACTIVE)
                .drugs(drugs)
                .build();

        ResponseEntity<PatientTrialWebModel> response = restTemplate.postForEntity(BASE_API_URL + PATIENT_TRIALS_PATH, patientTrialWebModel, PatientTrialWebModel.class);

        return response.getBody();
    }

    public static void deletePatientTrial(RestTemplate restTemplate, Long id) {
        restTemplate.delete(BASE_API_URL + PATIENT_TRIALS_PATH + DELETE_PATH, id);
    }

    public static PatientTrialWebModel getPatientTrial(RestTemplate restTemplate, Long id) {
        ResponseEntity<PatientTrialWebModel> response = restTemplate.getForEntity(BASE_API_URL + PATIENT_TRIALS_PATH + GET_PATH, PatientTrialWebModel.class, id);

        return response.getBody();
    }

}
