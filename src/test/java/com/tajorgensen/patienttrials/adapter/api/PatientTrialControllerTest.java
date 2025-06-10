package com.tajorgensen.patienttrials.adapter.api;

import com.tajorgensen.patienttrials.adapter.model.DrugWebModel;
import com.tajorgensen.patienttrials.adapter.model.PatientTrialWebModel;
import com.tajorgensen.patienttrials.common.constants.ApplicationConstants;
import com.tajorgensen.patienttrials.common.exception.ErrorDetails;
import com.tajorgensen.patienttrials.test.SystemTest;
import com.tajorgensen.patienttrials.test.SystemTestStagingUtils;
import com.tajorgensen.patienttrials.utils.DrugTestUtils;
import com.tajorgensen.patienttrials.utils.PatientTrialTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SystemTest
class PatientTrialControllerTest extends BaseControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private Long patientId;
    private Long trialId;
    private DrugWebModel drug;

    @BeforeEach
    void stageData() {
        patientId = SystemTestStagingUtils.stagePatient(restTemplate);
        trialId = SystemTestStagingUtils.stageTrial(restTemplate);
        drug = SystemTestStagingUtils.stageDrug(restTemplate);
    }

    @Test
    void testPatientTrialEndpoints() {
        PatientTrialWebModel webModel = PatientTrialTestUtils.createWebModel();
        webModel.setPatientId(patientId);
        webModel.setTrialId(trialId);
        drug.setDosage(DrugTestUtils.DOSAGE);
        drug.setDosageMeasurementType(DrugTestUtils.DOSAGE_MEASUREMENT_TYPE);
        webModel.setDrugs(List.of(drug));
        webModel.setId(null);

        // Create Adverse Event
        ResponseEntity<PatientTrialWebModel> createResponse = restTemplate.postForEntity("/patientTrials", webModel, PatientTrialWebModel.class);

        assertEquals(HttpStatusCode.valueOf(201), createResponse.getStatusCode());

        Long id = createResponse.getBody().getId();

        // Get Adverse Event
        ResponseEntity<PatientTrialWebModel> getResponse = restTemplate.getForEntity("/patientTrials/" + id, PatientTrialWebModel.class);

        assertEquals(HttpStatusCode.valueOf(200), getResponse.getStatusCode());

        PatientTrialWebModel webModelToUpdate = getResponse.getBody();
        webModel.setId(id);
        PatientTrialTestUtils.assertWebModelsEqual(webModel, webModelToUpdate);

        //Update Adverse Event
        webModelToUpdate.setTreatmentStatus(ApplicationConstants.TreatmentStatus.COMPLETED);

        ResponseEntity<PatientTrialWebModel> updateResponse = restTemplate.postForEntity("/patientTrials/" + id, webModelToUpdate, PatientTrialWebModel.class);

        assertEquals(HttpStatusCode.valueOf(200), updateResponse.getStatusCode());
        assertEquals(id, updateResponse.getBody().getId());

        // Verify Update to Adverse Event
        ResponseEntity<PatientTrialWebModel> verifyUpdateResponse = restTemplate.getForEntity("/patientTrials/" + id, PatientTrialWebModel.class);

        assertEquals(HttpStatusCode.valueOf(200), verifyUpdateResponse.getStatusCode());
        PatientTrialTestUtils.assertWebModelsEqual(webModelToUpdate, verifyUpdateResponse.getBody());

        // Delete Adverse Event
        restTemplate.delete("/patientTrials/" + id);

        ResponseEntity<ErrorDetails> confirmDeleteWorkedResponse = restTemplate.getForEntity("/patientTrials/" + id, ErrorDetails.class);

        assertEquals(HttpStatusCode.valueOf(204), confirmDeleteWorkedResponse.getStatusCode());
    }

}