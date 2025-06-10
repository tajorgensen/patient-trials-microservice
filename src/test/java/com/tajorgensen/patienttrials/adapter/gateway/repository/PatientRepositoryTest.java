package com.tajorgensen.patienttrials.adapter.gateway.repository;

import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.PatientEntity;
import com.tajorgensen.patienttrials.test.IntegrationTest;
import com.tajorgensen.patienttrials.utils.PatientTestUtils;
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
class PatientRepositoryTest {

    @Autowired
    private PatientRepository repository;

    @AfterEach
    public void cleanUp() {
        repository.deleteAll();
    }

    @Test
    void testCreatePatient() {
        PatientEntity entity = PatientTestUtils.createEntity();
        entity.setId(null);

        PatientEntity result = repository.save(entity);

        entity.setId(result.getId());
        PatientTestUtils.assertEntitiesEqual(entity, result);
    }

    @Test
    void testSaveIfExistsId_NotPresent() {
        PatientEntity entity = PatientTestUtils.createEntity();
        assertThrows(JpaObjectRetrievalFailureException.class, () -> repository.saveIfExists(entity));
    }

    @Test
    void testSaveIfExists() {
        PatientEntity entity = PatientTestUtils.createEntity();
        entity.setId(null);

        PatientEntity result = repository.save(entity);

        entity.setId(result.getId());
        entity.setMedicalHistory("New Medical History");

        result = repository.saveIfExists(entity);

        PatientTestUtils.assertEntitiesEqual(entity, result);
    }

    @Test
    void testFindById() {
        PatientEntity entity = PatientTestUtils.createEntity();
        entity.setId(null);

        PatientEntity result = repository.save(entity);
        Optional<PatientEntity> findResults = repository.findById(result.getId());

        entity.setId(result.getId());

        PatientTestUtils.assertEntitiesEqual(entity, findResults.get());
    }

    @Test
    void testFindById_NotPresent() {
        Optional<PatientEntity> entity = repository.findById(PatientTestUtils.ID);

        assertTrue(entity.isEmpty());
    }

    @Test
    void testDeleteById() {
        PatientEntity entity = PatientTestUtils.createEntity();
        entity.setId(null);

        PatientEntity result = repository.save(entity);

        assertDoesNotThrow(() -> repository.deleteById(result.getId()));
    }

    @Test
    void testDeleteById_NotPresent() {
        assertDoesNotThrow(() -> repository.deleteById(1L));
    }
}