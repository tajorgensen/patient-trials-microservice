package com.tajorgensen.patienttrials.adapter.api;

import com.tajorgensen.patienttrials.adapter.model.DrugWebModel;
import com.tajorgensen.patienttrials.adapter.model.PatientTrialWebModel;
import com.tajorgensen.patienttrials.adapter.model.TrialDrugAverageDosageListWebModel;
import com.tajorgensen.patienttrials.adapter.model.TrialDrugAverageDosageWebModel;
import com.tajorgensen.patienttrials.adapter.model.TrialWebModel;
import com.tajorgensen.patienttrials.common.constants.ApplicationConstants;
import com.tajorgensen.patienttrials.test.EndToEndTest;
import com.tajorgensen.patienttrials.utils.EndToEndTestUtils;
import com.tajorgensen.patienttrials.utils.TrialDrugAverageDosageTestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EndToEndTest
@ExtendWith(SpringExtension.class)
public class CalculateAverageDosageTest {

    private static final String AVERAGE_DOSAGE_PATH = EndToEndTestUtils.BASE_API_URL + EndToEndTestUtils.TRIALS_PATH + "/{trialId}/averageDosage";

    private static final String DRUG_NAME_ONE = "Test Drug One";
    private static final String DRUG_NAME_TWO = "Test Drug Two";
    private static final String DRUG_NAME_THREE = "Test Drug Three";

    private static final BigDecimal DOSAGE_AMOUNT_ONE = new BigDecimal(10);
    private static final BigDecimal DOSAGE_AMOUNT_TWO = new BigDecimal(100);
    private static final BigDecimal DOSAGE_AMOUNT_THREE = new BigDecimal(1000);

    private static final ApplicationConstants.UnitsOfMeasurement DOSAGE_MEASUREMENT_TYPE = ApplicationConstants.UnitsOfMeasurement.MILLIGRAMS;

    private ObjectMapper objectMapper;
    private RestTemplate restTemplate;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        restTemplate = new RestTemplate();
    }

    @Test
    void testCalculateAverageDosage_WithMultipleDrugsAndPatients() {
        // Stage Data
        List<Long> patientIds = new ArrayList<>();

        patientIds.add(EndToEndTestUtils.stagePatient(restTemplate));
        patientIds.add(EndToEndTestUtils.stagePatient(restTemplate));
        patientIds.add(EndToEndTestUtils.stagePatient(restTemplate));

        Map<String, DrugWebModel> drugs = new HashMap<>();

        drugs.put(DRUG_NAME_ONE, EndToEndTestUtils.stageDrug(restTemplate, DRUG_NAME_ONE));
        drugs.put(DRUG_NAME_TWO, EndToEndTestUtils.stageDrug(restTemplate, DRUG_NAME_TWO));
        drugs.put(DRUG_NAME_THREE, EndToEndTestUtils.stageDrug(restTemplate, DRUG_NAME_THREE));

        TrialWebModel trial = EndToEndTestUtils.stageTrial(restTemplate);

        DrugWebModel drugOne = DrugWebModel.builder().id(drugs.get(DRUG_NAME_ONE).getId()).dosage(DOSAGE_AMOUNT_ONE).dosageMeasurementType(DOSAGE_MEASUREMENT_TYPE).build();
        DrugWebModel drugTwo = DrugWebModel.builder().id(drugs.get(DRUG_NAME_TWO).getId()).dosage(DOSAGE_AMOUNT_TWO).dosageMeasurementType(DOSAGE_MEASUREMENT_TYPE).build();
        DrugWebModel drugThree = DrugWebModel.builder().id(drugs.get(DRUG_NAME_THREE).getId()).dosage(DOSAGE_AMOUNT_THREE).dosageMeasurementType(DOSAGE_MEASUREMENT_TYPE).build();

        PatientTrialWebModel patientTrialWebModelForPatientOne = EndToEndTestUtils.stagePatientTrial(restTemplate, patientIds.get(0), trial.getId(), List.of(drugOne, drugTwo, drugThree));
        PatientTrialWebModel patientTrialWebModelForPatientTwo = EndToEndTestUtils.stagePatientTrial(restTemplate, patientIds.get(1), trial.getId(), List.of(drugOne, drugTwo));
        PatientTrialWebModel patientTrialWebModelForPatientThree = EndToEndTestUtils.stagePatientTrial(restTemplate, patientIds.get(2), trial.getId(), List.of(drugOne));

        // Define expected response
        TrialDrugAverageDosageListWebModel expectedResponse = TrialDrugAverageDosageListWebModel
                .builder()
                .trialDrugs(List.of(
                        TrialDrugAverageDosageWebModel.builder()
                                .drugId(drugs.get(DRUG_NAME_ONE).getId().intValue())
                                .drugName(DRUG_NAME_ONE)
                                .trialId(trial.getId().intValue())
                                .trialName(trial.getName())
                                .averageDosage(DOSAGE_AMOUNT_ONE)
                                .dosageMeasurementType(DOSAGE_MEASUREMENT_TYPE)
                                .patientCount(3)
                                .build(),
                        TrialDrugAverageDosageWebModel.builder()
                                .drugId(drugs.get(DRUG_NAME_TWO).getId().intValue())
                                .drugName(DRUG_NAME_TWO)
                                .trialId(trial.getId().intValue())
                                .trialName(trial.getName())
                                .averageDosage(DOSAGE_AMOUNT_TWO)
                                .dosageMeasurementType(DOSAGE_MEASUREMENT_TYPE)
                                .patientCount(2)
                                .build(),
                        TrialDrugAverageDosageWebModel.builder()
                                .drugId(drugs.get(DRUG_NAME_THREE).getId().intValue())
                                .drugName(DRUG_NAME_THREE)
                                .trialId(trial.getId().intValue())
                                .trialName(trial.getName())
                                .averageDosage(DOSAGE_AMOUNT_THREE)
                                .dosageMeasurementType(DOSAGE_MEASUREMENT_TYPE)
                                .patientCount(1)
                                .build()
                ))
                .build();

        // Execute API call
        TrialDrugAverageDosageListWebModel actualResponse = restTemplate.getForObject(AVERAGE_DOSAGE_PATH, TrialDrugAverageDosageListWebModel.class, trial.getId());

        try {
            assertAll(
                    () -> assertEquals(expectedResponse.getTrialDrugs().size(), actualResponse.getTrialDrugs().size()),
                    () -> TrialDrugAverageDosageTestUtils.assertWebModelsEqual(expectedResponse.getTrialDrugs().get(0), actualResponse.getTrialDrugs().stream().filter(averageDosage -> expectedResponse.getTrialDrugs().get(0).getDrugId().compareTo(averageDosage.getDrugId()) == 0).findFirst().orElse(null)),
                    () -> TrialDrugAverageDosageTestUtils.assertWebModelsEqual(expectedResponse.getTrialDrugs().get(1), actualResponse.getTrialDrugs().stream().filter(averageDosage -> expectedResponse.getTrialDrugs().get(1).getDrugId().compareTo(averageDosage.getDrugId()) == 0).findFirst().orElse(null)),
                    () -> TrialDrugAverageDosageTestUtils.assertWebModelsEqual(expectedResponse.getTrialDrugs().get(2), actualResponse.getTrialDrugs().stream().filter(averageDosage -> expectedResponse.getTrialDrugs().get(2).getDrugId().compareTo(averageDosage.getDrugId()) == 0).findFirst().orElse(null))
            );
        } finally {
            // Cleanup staged data
            EndToEndTestUtils.deletePatientTrial(restTemplate, patientTrialWebModelForPatientOne.getId());
            EndToEndTestUtils.deletePatientTrial(restTemplate, patientTrialWebModelForPatientTwo.getId());
            EndToEndTestUtils.deletePatientTrial(restTemplate, patientTrialWebModelForPatientThree.getId());

            drugs.values().forEach(drug -> EndToEndTestUtils.deleteDrug(restTemplate, drug.getId()));

            EndToEndTestUtils.deleteTrial(restTemplate, trial.getId());

            patientIds.forEach(id -> EndToEndTestUtils.deletePatient(restTemplate, id));
        }
    }

    @Test
    void testCalculateAverageDosage_WithSingleDrugMultiplePatients() {
        // Stage Data
        List<Long> patientIds = new ArrayList<>();

        patientIds.add(EndToEndTestUtils.stagePatient(restTemplate));
        patientIds.add(EndToEndTestUtils.stagePatient(restTemplate));
        patientIds.add(EndToEndTestUtils.stagePatient(restTemplate));

        DrugWebModel drug = EndToEndTestUtils.stageDrug(restTemplate, DRUG_NAME_ONE);

        TrialWebModel trial = EndToEndTestUtils.stageTrial(restTemplate);

        DrugWebModel drugOne = DrugWebModel.builder().id(drug.getId()).dosage(DOSAGE_AMOUNT_ONE).dosageMeasurementType(DOSAGE_MEASUREMENT_TYPE).build();

        PatientTrialWebModel patientTrialWebModelForPatientOne = EndToEndTestUtils.stagePatientTrial(restTemplate, patientIds.get(0), trial.getId(), List.of(drugOne));
        PatientTrialWebModel patientTrialWebModelForPatientTwo = EndToEndTestUtils.stagePatientTrial(restTemplate, patientIds.get(0), trial.getId(), List.of(drugOne));
        PatientTrialWebModel patientTrialWebModelForPatientThree = EndToEndTestUtils.stagePatientTrial(restTemplate, patientIds.get(0), trial.getId(), List.of(drugOne));

        // Define expected response
        TrialDrugAverageDosageListWebModel expectedResponse = TrialDrugAverageDosageListWebModel
                .builder()
                .trialDrugs(List.of(
                        TrialDrugAverageDosageWebModel.builder()
                                .drugId(drug.getId().intValue())
                                .drugName(DRUG_NAME_ONE)
                                .trialId(trial.getId().intValue())
                                .trialName(trial.getName())
                                .averageDosage(DOSAGE_AMOUNT_ONE)
                                .dosageMeasurementType(DOSAGE_MEASUREMENT_TYPE)
                                .patientCount(3)
                                .build()
                ))
                .build();

        // Execute API call
        TrialDrugAverageDosageListWebModel actualResponse = restTemplate.getForObject(AVERAGE_DOSAGE_PATH, TrialDrugAverageDosageListWebModel.class, trial.getId());

        try {
            assertAll(
                    () -> assertEquals(expectedResponse.getTrialDrugs().size(), actualResponse.getTrialDrugs().size()),
                    () -> TrialDrugAverageDosageTestUtils.assertWebModelsEqual(expectedResponse.getTrialDrugs().get(0), actualResponse.getTrialDrugs().get(0))
            );
        } finally {
            // Cleanup staged data
            EndToEndTestUtils.deletePatientTrial(restTemplate, patientTrialWebModelForPatientOne.getId());
            EndToEndTestUtils.deletePatientTrial(restTemplate, patientTrialWebModelForPatientTwo.getId());
            EndToEndTestUtils.deletePatientTrial(restTemplate, patientTrialWebModelForPatientThree.getId());

            EndToEndTestUtils.deleteDrug(restTemplate, drug.getId());

            EndToEndTestUtils.deleteTrial(restTemplate, trial.getId());

            patientIds.forEach(id -> EndToEndTestUtils.deletePatient(restTemplate, id));
        }
    }

    @Test
    void testCalculateAverageDosage_WithMultipleDrugsSinglePatients() {
        // Stage Data
        Long patientId = EndToEndTestUtils.stagePatient(restTemplate);

        Map<String, DrugWebModel> drugs = new HashMap<>();

        drugs.put(DRUG_NAME_ONE, EndToEndTestUtils.stageDrug(restTemplate, DRUG_NAME_ONE));
        drugs.put(DRUG_NAME_TWO, EndToEndTestUtils.stageDrug(restTemplate, DRUG_NAME_TWO));
        drugs.put(DRUG_NAME_THREE, EndToEndTestUtils.stageDrug(restTemplate, DRUG_NAME_THREE));

        TrialWebModel trial = EndToEndTestUtils.stageTrial(restTemplate);

        DrugWebModel drugOne = DrugWebModel.builder().id(drugs.get(DRUG_NAME_ONE).getId()).dosage(DOSAGE_AMOUNT_ONE).dosageMeasurementType(DOSAGE_MEASUREMENT_TYPE).build();
        DrugWebModel drugTwo = DrugWebModel.builder().id(drugs.get(DRUG_NAME_TWO).getId()).dosage(DOSAGE_AMOUNT_TWO).dosageMeasurementType(DOSAGE_MEASUREMENT_TYPE).build();
        DrugWebModel drugThree = DrugWebModel.builder().id(drugs.get(DRUG_NAME_THREE).getId()).dosage(DOSAGE_AMOUNT_THREE).dosageMeasurementType(DOSAGE_MEASUREMENT_TYPE).build();

        PatientTrialWebModel patientTrialWebModelForPatientOne = EndToEndTestUtils.stagePatientTrial(restTemplate, patientId, trial.getId(), List.of(drugOne, drugTwo, drugThree));

        // Define expected response
        TrialDrugAverageDosageListWebModel expectedResponse = TrialDrugAverageDosageListWebModel
                .builder()
                .trialDrugs(List.of(
                        TrialDrugAverageDosageWebModel.builder()
                                .drugId(drugs.get(DRUG_NAME_ONE).getId().intValue())
                                .drugName(DRUG_NAME_ONE)
                                .trialId(trial.getId().intValue())
                                .trialName(trial.getName())
                                .averageDosage(DOSAGE_AMOUNT_ONE)
                                .dosageMeasurementType(DOSAGE_MEASUREMENT_TYPE)
                                .patientCount(1)
                                .build(),
                        TrialDrugAverageDosageWebModel.builder()
                                .drugId(drugs.get(DRUG_NAME_TWO).getId().intValue())
                                .drugName(DRUG_NAME_TWO)
                                .trialId(trial.getId().intValue())
                                .trialName(trial.getName())
                                .averageDosage(DOSAGE_AMOUNT_TWO)
                                .dosageMeasurementType(DOSAGE_MEASUREMENT_TYPE)
                                .patientCount(1)
                                .build(),
                        TrialDrugAverageDosageWebModel.builder()
                                .drugId(drugs.get(DRUG_NAME_THREE).getId().intValue())
                                .drugName(DRUG_NAME_THREE)
                                .trialId(trial.getId().intValue())
                                .trialName(trial.getName())
                                .averageDosage(DOSAGE_AMOUNT_THREE)
                                .dosageMeasurementType(DOSAGE_MEASUREMENT_TYPE)
                                .patientCount(1)
                                .build()
                ))
                .build();

        // Execute API call
        TrialDrugAverageDosageListWebModel actualResponse = restTemplate.getForObject(AVERAGE_DOSAGE_PATH, TrialDrugAverageDosageListWebModel.class, trial.getId());

        try {
            assertAll(
                    () -> assertEquals(expectedResponse.getTrialDrugs().size(), actualResponse.getTrialDrugs().size()),
                    () -> TrialDrugAverageDosageTestUtils.assertWebModelsEqual(expectedResponse.getTrialDrugs().get(0), actualResponse.getTrialDrugs().stream().filter(averageDosage -> expectedResponse.getTrialDrugs().get(0).getDrugId().compareTo(averageDosage.getDrugId()) == 0).findFirst().orElse(null)),
                    () -> TrialDrugAverageDosageTestUtils.assertWebModelsEqual(expectedResponse.getTrialDrugs().get(1), actualResponse.getTrialDrugs().stream().filter(averageDosage -> expectedResponse.getTrialDrugs().get(1).getDrugId().compareTo(averageDosage.getDrugId()) == 0).findFirst().orElse(null)),
                    () -> TrialDrugAverageDosageTestUtils.assertWebModelsEqual(expectedResponse.getTrialDrugs().get(2), actualResponse.getTrialDrugs().stream().filter(averageDosage -> expectedResponse.getTrialDrugs().get(2).getDrugId().compareTo(averageDosage.getDrugId()) == 0).findFirst().orElse(null))
            );
        } finally {
            // Cleanup staged data
            EndToEndTestUtils.deletePatientTrial(restTemplate, patientTrialWebModelForPatientOne.getId());

            drugs.values().forEach(drug -> EndToEndTestUtils.deleteDrug(restTemplate, drug.getId()));

            EndToEndTestUtils.deleteTrial(restTemplate, trial.getId());

            EndToEndTestUtils.deletePatient(restTemplate, patientId);
        }
    }

    @Test
    void testCalculateAverageDosage_TrialNotPresent() {
        // Stage Trial to get Id
        TrialWebModel trial = EndToEndTestUtils.stageTrial(restTemplate);

        // Delete to ensure trial with that id does not exist
        EndToEndTestUtils.deleteTrial(restTemplate, trial.getId());

        // Define expected response
        TrialDrugAverageDosageListWebModel expectedResponse = TrialDrugAverageDosageListWebModel.builder().trialDrugs(Collections.emptyList()).build();

        // Adding 1000 to trial id we created to avoid possible race condition using same id
        TrialDrugAverageDosageListWebModel actualResponse = restTemplate.getForObject(AVERAGE_DOSAGE_PATH, TrialDrugAverageDosageListWebModel.class, trial.getId() + 1000);

        assertAll(
                () -> assertNotNull(actualResponse.getTrialDrugs()),
                () -> assertTrue(actualResponse.getTrialDrugs().isEmpty())
        );
    }

}
