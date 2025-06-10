package com.tajorgensen.patienttrials.adapter.api;

import com.tajorgensen.patienttrials.adapter.model.AdverseEventWebModel;
import com.tajorgensen.patienttrials.common.constants.ApplicationConstants;
import com.tajorgensen.patienttrials.common.exception.ErrorDetails;
import com.tajorgensen.patienttrials.test.SystemTest;
import com.tajorgensen.patienttrials.test.SystemTestStagingUtils;
import com.tajorgensen.patienttrials.utils.AdverseEventTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SystemTest
class AdverseEventControllerTest extends BaseControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private Long patientId;
    private Long trialId;

    @BeforeEach
    void stageData() {
        patientId = SystemTestStagingUtils.stagePatient(restTemplate);
        trialId = SystemTestStagingUtils.stageTrial(restTemplate);
    }

    @Test
    void testAdverseEventEndpoints() {
        AdverseEventWebModel webModel = AdverseEventTestUtils.createWebModel();
        webModel.setPatientId(patientId);
        webModel.setTrialId(trialId);
        webModel.setId(null);

        // Create Adverse Event
        ResponseEntity<AdverseEventWebModel> createResponse = restTemplate.postForEntity("/adverseEvents", webModel, AdverseEventWebModel.class);

        assertEquals(HttpStatusCode.valueOf(201), createResponse.getStatusCode());

        Long id = createResponse.getBody().getId();

        // Get Adverse Event
        ResponseEntity<AdverseEventWebModel> getResponse = restTemplate.getForEntity("/adverseEvents/" + id, AdverseEventWebModel.class);

        assertEquals(HttpStatusCode.valueOf(200), getResponse.getStatusCode());

        AdverseEventWebModel webModelToUpdate = getResponse.getBody();
        webModel.setId(id);
        AdverseEventTestUtils.assertWebModelsEqual(webModel, webModelToUpdate);

        //Update Adverse Event
        webModelToUpdate.setEventSeverity(ApplicationConstants.EventSeverity.SEVERE);

        ResponseEntity<AdverseEventWebModel> updateResponse = restTemplate.postForEntity("/adverseEvents/" + id, webModelToUpdate, AdverseEventWebModel.class);

        assertEquals(HttpStatusCode.valueOf(200), updateResponse.getStatusCode());
        assertEquals(id, updateResponse.getBody().getId());

        // Verify Update to Adverse Event
        ResponseEntity<AdverseEventWebModel> verifyUpdateResponse = restTemplate.getForEntity("/adverseEvents/" + id, AdverseEventWebModel.class);

        assertEquals(HttpStatusCode.valueOf(200), verifyUpdateResponse.getStatusCode());
        AdverseEventTestUtils.assertWebModelsEqual(webModelToUpdate, verifyUpdateResponse.getBody());

        // Delete Adverse Event
        restTemplate.delete("/adverseEvents/" + id);

        ResponseEntity<ErrorDetails> confirmDeleteWorkedResponse = restTemplate.getForEntity("/adverseEvents/" + id, ErrorDetails.class);

        assertEquals(HttpStatusCode.valueOf(204), confirmDeleteWorkedResponse.getStatusCode());
    }

}