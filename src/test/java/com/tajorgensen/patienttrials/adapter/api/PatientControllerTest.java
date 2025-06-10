package com.tajorgensen.patienttrials.adapter.api;

import com.tajorgensen.patienttrials.adapter.model.PatientWebModel;
import com.tajorgensen.patienttrials.common.exception.ErrorDetails;
import com.tajorgensen.patienttrials.test.SystemTest;
import com.tajorgensen.patienttrials.utils.PatientTestUtils;
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
class PatientControllerTest extends BaseControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testPatientEndpoints() {
        PatientWebModel webModel = PatientTestUtils.createWebModel();
        webModel.setId(null);

        // Create Patient
        ResponseEntity<PatientWebModel> createResponse = restTemplate.postForEntity("/patients", webModel, PatientWebModel.class);

        assertEquals(HttpStatusCode.valueOf(201), createResponse.getStatusCode());

        Long id = createResponse.getBody().getId();

        // Get Patient
        ResponseEntity<PatientWebModel> getResponse = restTemplate.getForEntity("/patients/" + id, PatientWebModel.class);

        assertEquals(HttpStatusCode.valueOf(200), getResponse.getStatusCode());

        PatientWebModel webModelToUpdate = getResponse.getBody();
        webModel.setId(id);
        PatientTestUtils.assertWebModelsEqual(webModel, webModelToUpdate);

        //Update Patient
        webModelToUpdate.setMedicalHistory("New Medical History");

        ResponseEntity<PatientWebModel> updateResponse = restTemplate.postForEntity("/patients/" + id, webModelToUpdate, PatientWebModel.class);

        assertEquals(HttpStatusCode.valueOf(200), updateResponse.getStatusCode());
        assertEquals(id, updateResponse.getBody().getId());

        // Verify Update to Patient
        ResponseEntity<PatientWebModel> verifyUpdateResponse = restTemplate.getForEntity("/patients/" + id, PatientWebModel.class);

        assertEquals(HttpStatusCode.valueOf(200), verifyUpdateResponse.getStatusCode());
        PatientTestUtils.assertWebModelsEqual(webModelToUpdate, verifyUpdateResponse.getBody());

        // Delete Patient
        restTemplate.delete("/patients/" + id);

        ResponseEntity<ErrorDetails> confirmDeleteWorkedResponse = restTemplate.getForEntity("/patients/" + id, ErrorDetails.class);

        assertEquals(HttpStatusCode.valueOf(204), confirmDeleteWorkedResponse.getStatusCode());
    }

}