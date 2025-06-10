package com.tajorgensen.patienttrials.adapter.gateway.repository;

import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.PatientTrialDrugEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface PatientTrialDrugRepository extends JpaRepository<PatientTrialDrugEntity, Long> {

    /**
     * Find all patient trial drugs by patient trial id
     *
     * @param patientTrialId patient id
     * @return List containing the patient trial drugs for given patient trial id if found
     */
    List<PatientTrialDrugEntity> findAllByPatientTrialId(Long patientTrialId);

    /**
     * Delete patient trial by patient trial id
     *
     * @param patientTrialId patient trial id
     * @return Number of rows affected
     */
    @Modifying
    @Transactional
    int deleteAllByPatientTrialId(Long patientTrialId);

}
