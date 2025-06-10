package com.tajorgensen.patienttrials.adapter.gateway.repository;

import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.AdverseEventEntity;
import com.tajorgensen.patienttrials.test.IntegrationTest;
import com.tajorgensen.patienttrials.test.IntegrationTestStagingUtils;
import com.tajorgensen.patienttrials.utils.AdverseEventTestUtils;
import com.tajorgensen.patienttrials.utils.TrialTestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@IntegrationTest
@ActiveProfiles("junit")
class AdverseEventRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private TrialRepository trialRepository;

    @Autowired
    private AdverseEventRepository repository;

    @AfterEach
    public void cleanUp() {
        repository.deleteAll();
        patientRepository.deleteAll();
        trialRepository.deleteAll();
    }

    @Test
    void testCreateAdverseEvent() {
        AdverseEventEntity entity = stageDataForEntity();
        entity.setId(null);

        AdverseEventEntity result = repository.save(entity);

        entity.setId(result.getId());
        AdverseEventTestUtils.assertEntitiesEqual(entity, result);
    }

    @Test
    void testSaveIfExistsId_NotPresent() {
        AdverseEventEntity entity = stageDataForEntity();
        assertThrows(JpaObjectRetrievalFailureException.class, () -> repository.saveIfExists(entity));
    }

    @Test
    void testSaveIfExists() {
        AdverseEventEntity entity = stageDataForEntity();
        entity.setId(null);

        AdverseEventEntity result = repository.save(entity);

        entity.setId(result.getId());
        entity.setEventDescription("New Description");

        result = repository.saveIfExists(entity);

        AdverseEventTestUtils.assertEntitiesEqual(entity, result);
    }

    @Test
    void testFindByPatientIdAndTrialId() {
        AdverseEventEntity entity = stageDataForEntity();
        entity.setId(null);

        AdverseEventEntity result = repository.save(entity);
        List<AdverseEventEntity> findResults = repository.findByPatientIdAndTrialId(entity.getPatientId(), entity.getTrialId());

        entity.setId(result.getId());

        assertAll(
                () -> assertEquals(1, findResults.size()),
                () -> AdverseEventTestUtils.assertEntitiesEqual(entity, findResults.get(0))
        );
    }

    @Test
    void testFindByPatientIdAndTrialId_NotPresent() {
        List<AdverseEventEntity> entityList = repository.findByPatientIdAndTrialId(AdverseEventTestUtils.PATIENT_ID, AdverseEventTestUtils.TRIAL_ID);

        assertTrue(entityList.isEmpty());
    }

    @Test
    void testDeleteById() {
        AdverseEventEntity entity = stageDataForEntity();
        entity.setId(null);

        AdverseEventEntity result = repository.save(entity);

        assertDoesNotThrow(() -> repository.deleteById(result.getId()));
    }

    @Test
    void testDeleteById_NotPresent() {
        assertDoesNotThrow(() -> repository.deleteById(1L));
    }

    @Test
    void testFindByTrialId() {
        AdverseEventEntity entity = stageDataForEntity();
        entity.setId(null);

        AdverseEventEntity result = repository.save(entity);
        List<AdverseEventEntity> findResults = repository.findByTrialId(entity.getTrialId());

        entity.setId(result.getId());

        assertAll(
                () -> assertEquals(1, findResults.size()),
                () -> AdverseEventTestUtils.assertEntitiesEqual(entity, findResults.get(0))
        );
    }

    @Test
    void testFindByTrialId_NotPresent() {
        List<AdverseEventEntity> entityList = repository.findByTrialId(TrialTestUtils.ID);

        assertTrue(entityList.isEmpty());
    }

    private AdverseEventEntity stageDataForEntity() {
        // Stage data
        Long patientId = IntegrationTestStagingUtils.stagePatient(patientRepository);
        Long trialId = IntegrationTestStagingUtils.stageTrial(trialRepository);

        AdverseEventEntity entity = AdverseEventTestUtils.createEntity();
        entity.setPatientId(patientId);
        entity.setTrialId(trialId);
        return entity;
    }
}