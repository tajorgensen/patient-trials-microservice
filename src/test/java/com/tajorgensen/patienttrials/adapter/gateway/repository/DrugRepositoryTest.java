package com.tajorgensen.patienttrials.adapter.gateway.repository;

import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.DrugEntity;
import com.tajorgensen.patienttrials.test.IntegrationTest;
import com.tajorgensen.patienttrials.utils.DrugTestUtils;
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
class DrugRepositoryTest {

    @Autowired
    private DrugRepository repository;

    @AfterEach
    public void cleanUp() {
        repository.deleteAll();
    }

    @Test
    void testCreateDrug() {
        DrugEntity entity = DrugTestUtils.createEntity();
        entity.setId(null);

        DrugEntity result = repository.save(entity);

        entity.setId(result.getId());
        DrugTestUtils.assertEntitiesEqual(entity, result);
    }

    @Test
    void testSaveIfExistsId_NotPresent() {
        DrugEntity entity = DrugTestUtils.createEntity();
        assertThrows(JpaObjectRetrievalFailureException.class, () -> repository.saveIfExists(entity));
    }

    @Test
    void testSaveIfExists() {
        DrugEntity entity = DrugTestUtils.createEntity();
        entity.setId(null);

        DrugEntity result = repository.save(entity);

        entity.setId(result.getId());
        entity.setManufacturer("New Manufacturer");

        result = repository.saveIfExists(entity);

        DrugTestUtils.assertEntitiesEqual(entity, result);
    }

    @Test
    void testFindById() {
        DrugEntity entity = DrugTestUtils.createEntity();
        entity.setId(null);

        DrugEntity result = repository.save(entity);
        Optional<DrugEntity> findResults = repository.findById(result.getId());

        entity.setId(result.getId());

        DrugTestUtils.assertEntitiesEqual(entity, findResults.get());
    }

    @Test
    void testFindById_NotPresent() {
        Optional<DrugEntity> entity = repository.findById(DrugTestUtils.ID);

        assertTrue(entity.isEmpty());
    }

    @Test
    void testDeleteById() {
        DrugEntity entity = DrugTestUtils.createEntity();
        entity.setId(null);

        DrugEntity result = repository.save(entity);

        assertDoesNotThrow(() -> repository.deleteById(result.getId()));
    }

    @Test
    void testDeleteById_NotPresent() {
        assertDoesNotThrow(() -> repository.deleteById(1L));
    }
}