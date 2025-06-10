package com.tajorgensen.patienttrials.adapter.gateway.repository;

import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.AverageDrugDosageResultEntity;
import com.tajorgensen.patienttrials.common.constants.ApplicationConstants.Database;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalculateAverageDosageRepository extends JpaRepository<AverageDrugDosageResultEntity, AverageDrugDosageResultEntity.AverageDosageId> {

    /**
     * Calls the CalculateAverageDosage stored procedure to get average dosage data.
     *
     * @param trialId Optional trial ID to filter results, or null for all trials
     * @return List of AverageDosageDTO with the procedure results
     */
    @Procedure(name = Database.StoredProcedure.Name.CALCULATE_AVERAGE_DOSAGE)
    List<AverageDrugDosageResultEntity> calculateAverageDosage(@Param(Database.StoredProcedure.Parameters.TRIAL_ID) Integer trialId);
}
