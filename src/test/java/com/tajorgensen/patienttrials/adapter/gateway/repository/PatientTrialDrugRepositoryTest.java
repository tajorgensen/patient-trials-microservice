package com.tajorgensen.patienttrials.adapter.gateway.repository;

import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.PatientTrialDrugEntity;
import com.tajorgensen.patienttrials.test.IntegrationTest;
import com.tajorgensen.patienttrials.test.IntegrationTestStagingUtils;
import com.tajorgensen.patienttrials.utils.DrugTestUtils;
import com.tajorgensen.patienttrials.utils.PatientTrialTestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@IntegrationTest
@ActiveProfiles("junit")
class PatientTrialDrugRepositoryTest {

    @Autowired
    private PatientTrialDrugRepository repository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private TrialRepository trialRepository;

    @Autowired
    private PatientTrialRepository patientTrialRepository;

    @Autowired
    private DrugRepository drugRepository;

    @AfterEach
    public void cleanUp() {
        repository.deleteAll();
        patientTrialRepository.deleteAll();
        patientRepository.deleteAll();
        trialRepository.deleteAll();
        drugRepository.deleteAll();
    }

    @Test
    void testCreatePatientTrialDrug() {
        PatientTrialDrugEntity entity = stageDataForEntity();
        entity.setId(null);

        PatientTrialDrugEntity result = repository.save(entity);

        entity.setId(result.getId());
        DrugTestUtils.assertEntitiesEqual(entity, result);
    }

    @Test
    void testFindAllByPatientTrialId() {
        PatientTrialDrugEntity entity = stageDataForEntity();
        entity.setId(null);

        PatientTrialDrugEntity result = repository.save(entity);
        List<PatientTrialDrugEntity> findResults = repository.findAllByPatientTrialId(result.getId());

        entity.setId(result.getId());

        assertAll(
                () -> assertEquals(1, findResults.size()),
                () -> DrugTestUtils.assertEntitiesEqual(entity, findResults.get(0))
        );
    }

    @Test
    void testFindAllByPatientTrialId_NotPresent() {
        List<PatientTrialDrugEntity> entityList = repository.findAllByPatientTrialId(PatientTrialTestUtils.ID);

        assertTrue(entityList.isEmpty());
    }

    @Test
    void testDeleteAllByPatientTrialId() {
        PatientTrialDrugEntity entity = stageDataForEntity();
        entity.setId(null);

        PatientTrialDrugEntity result = repository.save(entity);

        Assertions.assertDoesNotThrow(() -> repository.deleteAllByPatientTrialId(result.getPatientTrialId()));
    }

    @Test
    void testDeleteAllByPatientTrialId_NotPresent() {
        Assertions.assertDoesNotThrow(() -> repository.deleteAllByPatientTrialId(1L));
    }

    private PatientTrialDrugEntity stageDataForEntity() {
        // Stage data
        Long drugId = IntegrationTestStagingUtils.stageDrug(drugRepository);
        Long patientTrialId = IntegrationTestStagingUtils.stagePatientTrial(patientTrialRepository, patientRepository, trialRepository);

        PatientTrialDrugEntity entity = DrugTestUtils.createPatientTrialDrugEntity();
        entity.setDrugId(drugId);
        entity.setPatientTrialId(patientTrialId);
        return entity;
    }
}