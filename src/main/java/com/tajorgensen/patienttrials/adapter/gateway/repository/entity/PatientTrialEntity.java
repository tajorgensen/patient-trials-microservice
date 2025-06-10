package com.tajorgensen.patienttrials.adapter.gateway.repository.entity;

import com.tajorgensen.patienttrials.common.constants.ApplicationConstants;
import com.tajorgensen.patienttrials.common.constants.ApplicationConstants.Database;
import com.tajorgensen.patienttrials.domain.model.PatientTrial;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;

@Entity
@Table(name = Database.TableName.PATIENT_TRIAL, schema = Database.Schema.DBO)
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PatientTrialEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = Database.ColumnName.ID)
    private Long id;

    @Column(name = Database.ColumnName.PATIENT_ID)
    private Long patientId;

    @Column(name = Database.ColumnName.TRIAL_ID)
    private Long trialId;

    @Column(name = Database.ColumnName.ENROLLMENT_DATE)
    private LocalDate enrollmentDate;

    @Column(name = Database.ColumnName.TREATMENT_STATUS)
    private String treatmentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Database.ColumnName.PATIENT_ID, insertable = false, updatable = false)
    private PatientEntity patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Database.ColumnName.TRIAL_ID, insertable = false, updatable = false)
    private TrialEntity trial;

    public PatientTrial toDomain() {
        return PatientTrial.builder()
                .id(this.id)
                .patientId(this.patientId)
                .patient(Optional.ofNullable(this.patient).map(PatientEntity::toDomain).orElse(null))
                .trialId(this.trialId)
                .trial(Optional.ofNullable(this.trial).map(TrialEntity::toDomain).orElse(null))
                .enrollmentDate(this.enrollmentDate)
                .treatmentStatus(ApplicationConstants.TreatmentStatus.valueOf(this.treatmentStatus))
                .build();
    }

    public static PatientTrialEntity of(PatientTrial domain) {
        if (domain == null) {
            return null;
        }

        return PatientTrialEntity.builder()
                .id(domain.getId())
                .patientId(domain.getPatientId())
                .trialId(domain.getTrialId())
                .enrollmentDate(domain.getEnrollmentDate())
                .treatmentStatus(domain.getTreatmentStatus().name())
                .build();
    }

}
