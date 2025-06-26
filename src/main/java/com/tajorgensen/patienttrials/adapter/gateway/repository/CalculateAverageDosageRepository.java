package com.tajorgensen.patienttrials.adapter.gateway.repository;

import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.AverageDrugDosageResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalculateAverageDosageRepository extends JpaRepository<AverageDrugDosageResultEntity, AverageDrugDosageResultEntity.AverageDosageId> {

    /**
     * Calls the PostgreSQL function to get average dosage data.
     *
     * @param trialId Optional trial ID to filter results, or null for all trials
     * @return List of AverageDrugDosageResultEntity with the function results
     */
    @Query(nativeQuery = true, name = "AverageDrugDosageResultEntity.calculateAverageDosage")
    List<AverageDrugDosageResultEntity> calculateAverageDosage(@Param("trialId") Integer trialId);
}