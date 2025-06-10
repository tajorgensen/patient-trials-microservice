package com.tajorgensen.patienttrials.adapter.api;

import com.tajorgensen.patienttrials.adapter.model.DrugWebModel;
import com.tajorgensen.patienttrials.common.exception.ErrorDetails;
import com.tajorgensen.patienttrials.test.SystemTest;
import com.tajorgensen.patienttrials.utils.DrugTestUtils;
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
class DrugControllerTest extends BaseControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testDrugEndpoints() {
        DrugWebModel webModel = DrugTestUtils.createWebModel();
        webModel.setId(null);

        // Create Drug
        ResponseEntity<DrugWebModel> createResponse = restTemplate.postForEntity("/drugs", webModel, DrugWebModel.class);

        assertEquals(HttpStatusCode.valueOf(201), createResponse.getStatusCode());

        Long id = createResponse.getBody().getId();

        // Get Drug
        ResponseEntity<DrugWebModel> getResponse = restTemplate.getForEntity("/drugs/" + id, DrugWebModel.class);

        assertEquals(HttpStatusCode.valueOf(200), getResponse.getStatusCode());

        DrugWebModel webModelToUpdate = getResponse.getBody();
        webModel.setId(id);
        DrugTestUtils.assertWebModelsEqual(webModel, webModelToUpdate);

        //Update Drug
        webModelToUpdate.setManufacturer("New Manufacturer");

        ResponseEntity<DrugWebModel> updateResponse = restTemplate.postForEntity("/drugs/" + id, webModelToUpdate, DrugWebModel.class);

        assertEquals(HttpStatusCode.valueOf(200), updateResponse.getStatusCode());
        assertEquals(id, updateResponse.getBody().getId());

        // Verify Update to Drug
        ResponseEntity<DrugWebModel> verifyUpdateResponse = restTemplate.getForEntity("/drugs/" + id, DrugWebModel.class);

        assertEquals(HttpStatusCode.valueOf(200), verifyUpdateResponse.getStatusCode());
        DrugTestUtils.assertWebModelsEqual(webModelToUpdate, verifyUpdateResponse.getBody());

        // Delete Drug
        restTemplate.delete("/drugs/" + id);

        ResponseEntity<ErrorDetails> confirmDeleteWorkedResponse = restTemplate.getForEntity("/drugs/" + id, ErrorDetails.class);

        assertEquals(HttpStatusCode.valueOf(204), confirmDeleteWorkedResponse.getStatusCode());
    }

}