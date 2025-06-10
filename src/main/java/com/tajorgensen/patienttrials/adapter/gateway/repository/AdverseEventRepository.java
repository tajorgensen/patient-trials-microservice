package com.tajorgensen.patienttrials.adapter.gateway.repository;

import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.AdverseEventEntity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdverseEventRepository extends JpaRepository<AdverseEventEntity, Long> {

    /**
     * Confirm existence of entity prior to saving
     *
     * @param entity
     * @return saved entity if id already exists
     * @throws JpaObjectRetrievalFailureException if id not present in database
     */
    default AdverseEventEntity saveIfExists(AdverseEventEntity entity) {
        if (existsById(entity.getId())) {
            return save(entity);
        }

        throw new JpaObjectRetrievalFailureException(new EntityNotFoundException());
    }

    /**
     * Find adverse events by patient id and trial id
     *
     * @param patientId patient id
     * @param trialId   trial id
     * @return List containing the adverse events if found
     */
    List<AdverseEventEntity> findByPatientIdAndTrialId(Long patientId, Long trialId);

    /**
     * Delete adverse events by patient id and trial id
     *
     * @param patientId patient id
     * @param trialId   trial id
     * @return Number of rows affected
     */
    @Modifying
    @Transactional
    int deleteByPatientIdAndTrialId(Long patientId, Long trialId);
    // TODO add this call to the delete patient trial client

    /**
     * Find adverse events by trial id
     *
     * @param trialId trial id
     * @return List containing the adverse events if found
     */
    List<AdverseEventEntity> findByTrialId(Long trialId);

}
