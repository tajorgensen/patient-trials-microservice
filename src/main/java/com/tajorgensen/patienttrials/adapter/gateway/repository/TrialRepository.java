package com.tajorgensen.patienttrials.adapter.gateway.repository;

import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.TrialEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrialRepository extends JpaRepository<TrialEntity, Long> {

    /**
     * Find trial by id
     *
     * @param id Trial id
     * @return Optional containing the trial if found
     */
    Optional<TrialEntity> findById(Long id);

    /**
     * Confirm existence of entity prior to saving
     *
     * @param entity
     * @return saved entity if id already exists
     * @throws JpaObjectRetrievalFailureException if id not present in database
     */
    default TrialEntity saveIfExists(TrialEntity entity) {
        if (existsById(entity.getId())) {
            return save(entity);
        }

        throw new JpaObjectRetrievalFailureException(new EntityNotFoundException());
    }
}
