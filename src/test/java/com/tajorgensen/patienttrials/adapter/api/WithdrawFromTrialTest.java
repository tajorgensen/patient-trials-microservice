package com.tajorgensen.patienttrials.adapter.api;

import com.tajorgensen.patienttrials.adapter.model.AdverseEventWebModel;
import com.tajorgensen.patienttrials.adapter.model.DrugWebModel;
import com.tajorgensen.patienttrials.adapter.model.PatientTrialWebModel;
import com.tajorgensen.patienttrials.adapter.model.TrialWebModel;
import com.tajorgensen.patienttrials.common.constants.ApplicationConstants;
import com.tajorgensen.patienttrials.test.EndToEndTest;
import com.tajorgensen.patienttrials.utils.DrugTestUtils;
import com.tajorgensen.patienttrials.utils.EndToEndTestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@EndToEndTest
@ExtendWith(SpringExtension.class)
public class WithdrawFromTrialTest {

    private static final String ADVERSE_EVENTS_PATH = "/adverseEvents";

    private static final BigDecimal DOSAGE_AMOUNT_ONE = new BigDecimal(10);
    private static final ApplicationConstants.UnitsOfMeasurement DOSAGE_MEASUREMENT_TYPE = ApplicationConstants.UnitsOfMeasurement.MILLIGRAMS;
    private static final String SEVERE_EVENT_DESCRIPTION = "Heart Attack";


    private ObjectMapper objectMapper;
    private RestTemplate restTemplate;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        restTemplate = new RestTemplate();
    }

    @Test
    void testWhenSevereAdverseEventIsCreatedPatientIsWithdrawnFromTrial() {
        // Stage data
        Long patientId = EndToEndTestUtils.stagePatient(restTemplate);

        DrugWebModel drug = EndToEndTestUtils.stageDrug(restTemplate, DrugTestUtils.NAME);

        TrialWebModel trial = EndToEndTestUtils.stageTrial(restTemplate);

        DrugWebModel trialDrug = DrugWebModel.builder().id(drug.getId()).dosage(DOSAGE_AMOUNT_ONE).dosageMeasurementType(DOSAGE_MEASUREMENT_TYPE).build();

        PatientTrialWebModel patientTrialForPatientOne = EndToEndTestUtils.stagePatientTrial(restTemplate, patientId, trial.getId(), List.of(trialDrug));

        AdverseEventWebModel adverseEvent = AdverseEventWebModel.builder().trialId(trial.getId()).patientId(patientId).eventSeverity(ApplicationConstants.EventSeverity.SEVERE).eventDescription(SEVERE_EVENT_DESCRIPTION).build();

        // Create Severe Adverse Event to Trigger Withdrawing patient from trial
        ResponseEntity<AdverseEventWebModel> adverseEventResponse = restTemplate.postForEntity(EndToEndTestUtils.BASE_API_URL + ADVERSE_EVENTS_PATH, adverseEvent, AdverseEventWebModel.class);

        PatientTrialWebModel patientTrialResponse = EndToEndTestUtils.getPatientTrial(restTemplate, patientTrialForPatientOne.getId());

        try {
            Assertions.assertEquals(ApplicationConstants.TreatmentStatus.WITHDRAWN, patientTrialResponse.getTreatmentStatus());
        } finally {
            // clean up
            EndToEndTestUtils.deletePatientTrial(restTemplate, patientTrialForPatientOne.getId());
            restTemplate.delete(EndToEndTestUtils.BASE_API_URL + ADVERSE_EVENTS_PATH + EndToEndTestUtils.DELETE_PATH, adverseEventResponse.getBody().getId());
            EndToEndTestUtils.deleteDrug(restTemplate, drug.getId());
            EndToEndTestUtils.deleteTrial(restTemplate, trial.getId());
            EndToEndTestUtils.deletePatient(restTemplate, patientId);
        }

    }


}
