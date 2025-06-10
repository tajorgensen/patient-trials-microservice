package com.tajorgensen.patienttrials.adapter.gateway.repository;

import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.PatientEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

    /**
     * Find patient by id
     *
     * @param id Patient id
     * @return Optional containing the patient if found
     */
    Optional<PatientEntity> findById(Long id);

    /**
     * Confirm existence of entity prior to saving
     *
     * @param entity
     * @return saved entity if id already exists
     * @throws JpaObjectRetrievalFailureException if id not present in database
     */
    default PatientEntity saveIfExists(PatientEntity entity) {
        if (existsById(entity.getId())) {
            return save(entity);
        }

        throw new JpaObjectRetrievalFailureException(new EntityNotFoundException());
    }
}
