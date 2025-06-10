package com.tajorgensen.patienttrials.adapter.gateway.repository;

import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.PatientTrialEntity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientTrialRepository extends JpaRepository<PatientTrialEntity, Long> {

    /**
     * Confirm existence of entity prior to saving
     *
     * @param entity
     * @return saved entity if id already exists
     * @throws JpaObjectRetrievalFailureException if id not present in database
     */
    default PatientTrialEntity saveIfExists(PatientTrialEntity entity) {
        if (existsById(entity.getId())) {
            return save(entity);
        }

        throw new JpaObjectRetrievalFailureException(new EntityNotFoundException());
    }
    
    @Query("SELECT pt FROM PatientTrialEntity pt LEFT JOIN FETCH pt.patient LEFT JOIN FETCH pt.trial WHERE pt.trialId = :trialId")
    @QueryHints({
            @QueryHint(name = "jakarta.persistence.cache.retrieveMode", value = "BYPASS")
    })
    List<PatientTrialEntity> findByTrialIdWithEagerRelationships(@Param("trialId") Long trialId);

}
