package com.tajorgensen.patienttrials.adapter.gateway.repository;

import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.TrialEntity;
import com.tajorgensen.patienttrials.test.IntegrationTest;
import com.tajorgensen.patienttrials.utils.TrialTestUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@IntegrationTest
@ActiveProfiles("junit")
class TrialRepositoryTest {

    @Autowired
    private TrialRepository repository;

    @AfterEach
    public void cleanUp() {
        repository.deleteAll();
    }

    @Test
    void testCreateTrial() {
        TrialEntity entity = TrialTestUtils.createEntity();
        entity.setId(null);

        TrialEntity result = repository.save(entity);

        entity.setId(result.getId());
        TrialTestUtils.assertEntitiesEqual(entity, result);
    }

    @Test
    void testSaveIfExists_IdNotPresent() {
        TrialEntity entity = TrialTestUtils.createEntity();
        assertThrows(JpaObjectRetrievalFailureException.class, () -> repository.saveIfExists(entity));
    }

    @Test
    void testSaveIfExists() {
        TrialEntity entity = TrialTestUtils.createEntity();
        entity.setId(null);

        TrialEntity result = repository.save(entity);

        entity.setId(result.getId());
        entity.setProtocolDescription("New Description");

        result = repository.saveIfExists(entity);

        TrialTestUtils.assertEntitiesEqual(entity, result);
    }

    @Test
    void testFindById() {
        TrialEntity entity = TrialTestUtils.createEntity();
        entity.setId(null);

        TrialEntity result = repository.save(entity);
        Optional<TrialEntity> findResults = repository.findById(result.getId());

        entity.setId(result.getId());

        TrialTestUtils.assertEntitiesEqual(entity, findResults.get());
    }

    @Test
    void testFindById_NotPresent() {
        Optional<TrialEntity> entity = repository.findById(TrialTestUtils.ID);

        assertTrue(entity.isEmpty());
    }

    @Test
    void testDeleteById() {
        TrialEntity entity = TrialTestUtils.createEntity();
        entity.setId(null);

        TrialEntity result = repository.save(entity);

        assertDoesNotThrow(() -> repository.deleteById(result.getId()));
    }

    @Test
    void testDeleteById_NotPresent() {
        assertDoesNotThrow(() -> repository.deleteById(1L));
    }

    @Test
    void testFindAll_SingleExists() {
        TrialEntity entity = stageEntity(null);

        List<TrialEntity> entityList = repository.findAll();

        assertEquals(1, entityList.size());

        entity.setId(entityList.get(0).getId());
        TrialTestUtils.assertEntitiesEqual(entity, entityList.get(0));
    }

    @Test
    void testFindAll_MultipleExist() {
        TrialEntity entityOne = stageEntity(null);
        TrialEntity entityTwo = stageEntity("different description");

        List<TrialEntity> entityList = repository.findAll();

        assertEquals(2, entityList.size());

        entityOne.setId(entityList.get(0).getId());
        entityTwo.setId(entityList.get(1).getId());

        assertAll(
                () -> TrialTestUtils.assertEntitiesEqual(entityOne, entityList.get(0)),
                () -> TrialTestUtils.assertEntitiesEqual(entityTwo, entityList.get(1))
        );
    }

    @Test
    void testFindAll_NoneExist() {
        List<TrialEntity> entityList = repository.findAll();

        assertAll(
                () -> assertNotNull(entityList),
                () -> assertTrue(entityList.isEmpty())
        );
    }

    private TrialEntity stageEntity(String description) {
        TrialEntity entity = TrialTestUtils.createEntity();
        entity.setId(null);

        if (StringUtils.isNotBlank(description)) {
            entity.setProtocolDescription(description);
        }

        return repository.save(entity);
    }
}