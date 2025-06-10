package com.tajorgensen.patienttrials.adapter.gateway.repository;

import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.PatientTrialEntity;
import com.tajorgensen.patienttrials.common.constants.ApplicationConstants;
import com.tajorgensen.patienttrials.test.IntegrationTest;
import com.tajorgensen.patienttrials.test.IntegrationTestStagingUtils;
import com.tajorgensen.patienttrials.utils.PatientTrialTestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@IntegrationTest
@ActiveProfiles("junit")
class PatientTrialRepositoryTest {

    @Autowired
    private PatientTrialRepository repository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private TrialRepository trialRepository;

    @AfterEach
    public void cleanUp() {
        repository.deleteAll();
        patientRepository.deleteAll();
        trialRepository.deleteAll();
    }

    @Test
    void testCreatePatientTrial() {
        PatientTrialEntity entity = stageDataForEntity();
        entity.setId(null);

        PatientTrialEntity result = repository.save(entity);

        entity.setId(result.getId());
        PatientTrialTestUtils.assertEntitiesEqual(entity, result);
    }

    @Test
    void testSaveIfExistsId_NotPresent() {
        PatientTrialEntity entity = stageDataForEntity();
        assertThrows(JpaObjectRetrievalFailureException.class, () -> repository.saveIfExists(entity));
    }

    @Test
    void testSaveIfExists() {
        PatientTrialEntity entity = stageDataForEntity();
        entity.setId(null);

        PatientTrialEntity result = repository.save(entity);

        entity.setId(result.getId());
        entity.setTreatmentStatus(ApplicationConstants.TreatmentStatus.COMPLETED.name());

        result = repository.saveIfExists(entity);

        PatientTrialTestUtils.assertEntitiesEqual(entity, result);
    }

    @Test
    void testFindById() {
        PatientTrialEntity entity = stageDataForEntity();
        entity.setId(null);

        PatientTrialEntity result = repository.save(entity);
        Optional<PatientTrialEntity> findResults = repository.findById(result.getId());

        entity.setId(result.getId());

        PatientTrialTestUtils.assertEntitiesEqual(entity, findResults.get());
    }

    @Test
    void testFindById_NotPresent() {
        Optional<PatientTrialEntity> entity = repository.findById(PatientTrialTestUtils.ID);

        assertTrue(entity.isEmpty());
    }

    @Test
    void testDeleteById() {
        PatientTrialEntity entity = stageDataForEntity();
        entity.setId(null);

        PatientTrialEntity result = repository.save(entity);

        assertDoesNotThrow(() -> repository.deleteById(result.getId()));
    }

    @Test
    void testDeleteById_NotPresent() {
        assertDoesNotThrow(() -> repository.deleteById(1L));
    }


    private PatientTrialEntity stageDataForEntity() {
        // Stage data
        Long patientId = IntegrationTestStagingUtils.stagePatient(patientRepository);
        Long trialId = IntegrationTestStagingUtils.stageTrial(trialRepository);

        PatientTrialEntity entity = PatientTrialTestUtils.createEntity();
        entity.setPatientId(patientId);
        entity.setTrialId(trialId);
        return entity;
    }
}