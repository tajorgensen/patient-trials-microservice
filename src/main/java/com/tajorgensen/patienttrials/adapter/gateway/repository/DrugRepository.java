package com.tajorgensen.patienttrials.adapter.gateway.repository;

import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.DrugEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DrugRepository extends JpaRepository<DrugEntity, Long> {

    /**
     * Find drug by id
     *
     * @param id Drug id
     * @return Optional containing the drug if found
     */
    Optional<DrugEntity> findById(Long id);

    /**
     * Confirm existence of entity prior to saving
     *
     * @param entity
     * @return saved entity if id already exists
     * @throws JpaObjectRetrievalFailureException if id not present in database
     */
    default DrugEntity saveIfExists(DrugEntity entity) {
        if (existsById(entity.getId())) {
            return save(entity);
        }

        throw new JpaObjectRetrievalFailureException(new EntityNotFoundException());
    }

}
