package com.tajorgensen.patienttrials.adapter.api;

import com.tajorgensen.patienttrials.adapter.gateway.repository.PatientRepository;
import com.tajorgensen.patienttrials.adapter.gateway.repository.PatientTrialRepository;
import com.tajorgensen.patienttrials.adapter.gateway.repository.TrialRepository;
import com.tajorgensen.patienttrials.adapter.model.PatientTrialStatusWebModel;
import com.tajorgensen.patienttrials.adapter.model.PatientTrialWebModel;
import com.tajorgensen.patienttrials.adapter.model.PatientWebModel;
import com.tajorgensen.patienttrials.adapter.model.TrialListWebModel;
import com.tajorgensen.patienttrials.adapter.model.TrialPatientListWebModel;
import com.tajorgensen.patienttrials.adapter.model.TrialWebModel;
import com.tajorgensen.patienttrials.common.exception.ErrorDetails;
import com.tajorgensen.patienttrials.test.SystemTest;
import com.tajorgensen.patienttrials.test.SystemTestStagingUtils;
import com.tajorgensen.patienttrials.utils.PatientTestUtils;
import com.tajorgensen.patienttrials.utils.TrialPatientListTestUtils;
import com.tajorgensen.patienttrials.utils.TrialTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SystemTest
class TrialControllerTest extends BaseControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientTrialRepository patientTrialRepository;

    @Autowired
    private TrialRepository trialRepository;

    @Test
    void testTrialEndpoints() {
        TrialWebModel webModel = TrialTestUtils.createWebModel();
        webModel.setId(null);

        // Create Trial
        ResponseEntity<TrialWebModel> createResponse = restTemplate.postForEntity("/trials", webModel, TrialWebModel.class);

        assertEquals(HttpStatusCode.valueOf(201), createResponse.getStatusCode());

        Long id = createResponse.getBody().getId();

        // Get Trial
        ResponseEntity<TrialWebModel> getResponse = restTemplate.getForEntity("/trials/" + id, TrialWebModel.class);

        assertEquals(HttpStatusCode.valueOf(200), getResponse.getStatusCode());

        TrialWebModel webModelToUpdate = getResponse.getBody();
        webModel.setId(id);
        TrialTestUtils.assertWebModelsEqual(webModel, webModelToUpdate);

        //Update Trial
        webModelToUpdate.setProtocolDescription("New Description");

        ResponseEntity<TrialWebModel> updateResponse = restTemplate.postForEntity("/trials/" + id, webModelToUpdate, TrialWebModel.class);

        assertEquals(HttpStatusCode.valueOf(200), updateResponse.getStatusCode());
        assertEquals(id, updateResponse.getBody().getId());

        // Verify Update to Trial using the getAll
        ResponseEntity<TrialListWebModel> verifyUpdateResponse = restTemplate.getForEntity("/trials", TrialListWebModel.class);

        assertEquals(HttpStatusCode.valueOf(200), verifyUpdateResponse.getStatusCode());
        TrialListWebModel trialListWebModel = verifyUpdateResponse.getBody();

        assertFalse(trialListWebModel.getTrials().isEmpty());
        TrialTestUtils.assertWebModelsEqual(webModelToUpdate, trialListWebModel.getTrials().get(0));

        // Delete Trial
        restTemplate.delete("/trials/" + id);

        ResponseEntity<ErrorDetails> confirmDeleteWorkedResponse = restTemplate.getForEntity("/trials/" + id, ErrorDetails.class);

        assertEquals(HttpStatusCode.valueOf(204), confirmDeleteWorkedResponse.getStatusCode());
    }

    @Test
    void testGetAllPatientsForTrial() throws Exception {
        Long patientId = SystemTestStagingUtils.stagePatient(restTemplate);
        Long trialId = SystemTestStagingUtils.stageTrial(restTemplate);
        PatientTrialWebModel patientTrial = SystemTestStagingUtils.stagePatientTrial(restTemplate, patientId, trialId);

        PatientWebModel expectedPatient = PatientTestUtils.createWebModel();
        expectedPatient.setId(patientId);

        PatientTrialStatusWebModel expectedPatientTrialStatus = new PatientTrialStatusWebModel();
        expectedPatientTrialStatus.setPatient(expectedPatient);
        expectedPatientTrialStatus.setPatientTrialId(patientTrial.getId());
        expectedPatientTrialStatus.setTreatmentStatus(patientTrial.getTreatmentStatus());
        expectedPatientTrialStatus.setEnrollmentDate(patientTrial.getEnrollmentDate());

        TrialPatientListWebModel expectedTrialPatientList = new TrialPatientListWebModel();
        expectedTrialPatientList.setPatients(List.of(expectedPatientTrialStatus));

        // Get All patients for a given trial
        ResponseEntity<TrialPatientListWebModel> getAllPatients = restTemplate.getForEntity("/trials/" + trialId + "/patients", TrialPatientListWebModel.class);

        TrialPatientListWebModel trialPatientList = getAllPatients.getBody();

        TrialPatientListTestUtils.assertWebModelsEqual(expectedTrialPatientList, trialPatientList);

        // delete staged data directly through repos since delete endpoints are tested elsewhere already
        patientTrialRepository.deleteById(patientTrial.getId());
        patientRepository.deleteById(patientId);
        trialRepository.deleteById(trialId);
    }

}