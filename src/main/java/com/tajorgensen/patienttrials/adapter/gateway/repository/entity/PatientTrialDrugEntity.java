package com.tajorgensen.patienttrials.adapter.gateway.repository.entity;

import com.tajorgensen.patienttrials.common.constants.ApplicationConstants;
import com.tajorgensen.patienttrials.common.constants.ApplicationConstants.Database;
import com.tajorgensen.patienttrials.domain.model.Drug;
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

import java.math.BigDecimal;
import java.util.Optional;

@Entity
@Table(name = Database.TableName.PATIENT_TRIAL_DRUG, schema = Database.Schema.DBO)
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PatientTrialDrugEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = Database.ColumnName.ID)
    private Long id;

    @Column(name = Database.ColumnName.PATIENT_TRIAL_ID)
    private Long patientTrialId;

    @Column(name = Database.ColumnName.DRUG_ID)
    private Long drugId;

    @Column(name = Database.ColumnName.DOSAGE)
    private BigDecimal dosage;

    @Column(name = Database.ColumnName.DOSAGE_MEASUREMENT_TYPE)
    private String dosageMeasurementType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Database.ColumnName.PATIENT_TRIAL_ID, insertable = false, updatable = false)
    private PatientTrialEntity patientTrial;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = Database.ColumnName.DRUG_ID, insertable = false, updatable = false)
    private DrugEntity drug;

    public Drug toDomain() {
        return Drug.builder()
                .id(this.drugId)
                .name(Optional.ofNullable(this.drug).map(DrugEntity::getName).orElse(null))
                .chemicalFormula(Optional.ofNullable(this.drug).map(DrugEntity::getChemicalFormula).orElse(null))
                .manufacturer(Optional.ofNullable(this.drug).map(DrugEntity::getManufacturer).orElse(null))
                .dosage(this.dosage)
                .dosageMeasurementType(ApplicationConstants.UnitsOfMeasurement.valueOf(this.dosageMeasurementType))
                .build();
    }

    public static PatientTrialDrugEntity of(Drug domain, Long patientTrialId) {
        if (domain == null) {
            return null;
        }

        return PatientTrialDrugEntity.builder()
                .patientTrialId(patientTrialId)
                .drugId(domain.getId())
                .dosage(domain.getDosage())
                .dosageMeasurementType(Optional.ofNullable(domain.getDosageMeasurementType()).map(ApplicationConstants.UnitsOfMeasurement::name).orElse(null))
                .build();
    }

}
