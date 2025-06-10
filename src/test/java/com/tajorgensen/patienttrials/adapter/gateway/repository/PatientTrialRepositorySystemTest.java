package com.tajorgensen.patienttrials.adapter.gateway.repository;

import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.PatientEntity;
import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.PatientTrialEntity;
import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.TrialEntity;
import com.tajorgensen.patienttrials.test.IntegrationTestStagingUtils;
import com.tajorgensen.patienttrials.test.SystemTest;
import com.tajorgensen.patienttrials.utils.PatientTestUtils;
import com.tajorgensen.patienttrials.utils.PatientTrialTestUtils;
import com.tajorgensen.patienttrials.utils.TrialTestUtils;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SystemTest
@SpringBootTest
public class PatientTrialRepositorySystemTest {

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
    void testFindAllByTrialIdWithEagerFetch() {
        PatientTrialEntity entityOne = stageDataForEntity();
        entityOne.setId(null);

        PatientTrialEntity entityOneResult = repository.save(entityOne);
        entityOne.setId(entityOneResult.getId());

        PatientTrialEntity entityTwo = stageDataForEntity();
        entityTwo.setId(null);

        repository.save(entityTwo);

        List<PatientTrialEntity> entityList = repository.findByTrialIdWithEagerRelationships(entityOne.getTrialId());

        assertEquals(1, entityList.size());
        PatientTrialTestUtils.assertEntitiesEqual(entityOneResult, entityList.get(0));

        PatientEntity patientEntity = PatientTestUtils.createEntity();
        patientEntity.setId(entityOne.getPatientId());

        TrialEntity trialEntity = TrialTestUtils.createEntity();
        trialEntity.setId(entityOne.getTrialId());

        assertAll(
                () -> assertTrue(Hibernate.isInitialized(entityList.get(0).getPatient())),
                () -> assertTrue(Hibernate.isInitialized(entityList.get(0).getTrial())),
                () -> PatientTestUtils.assertEntitiesEqual(patientEntity, entityList.get(0).getPatient()),
                () -> TrialTestUtils.assertEntitiesEqual(trialEntity, entityList.get(0).getTrial())
        );

    }

    @Test
    void testFindAllByTrialIdWithEagerFetch_NonePresent() {
        PatientTrialEntity entityOne = stageDataForEntity();
        entityOne.setId(null);

        PatientTrialEntity entityOneResult = repository.save(entityOne);
        entityOne.setId(entityOneResult.getId());

        List<PatientTrialEntity> entityList = repository.findByTrialIdWithEagerRelationships(entityOne.getTrialId() + 1);

        assertTrue(entityList.isEmpty());
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
