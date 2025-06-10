package com.tajorgensen.patienttrials.adapter.gateway.repository.entity;

import com.tajorgensen.patienttrials.common.constants.ApplicationConstants;
import com.tajorgensen.patienttrials.common.constants.ApplicationConstants.Database;
import com.tajorgensen.patienttrials.domain.model.AverageDrugDosageResult;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.StoredProcedureParameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@IdClass(AverageDrugDosageResultEntity.AverageDosageId.class)
@SqlResultSetMapping(
        name = "AverageDosageMapping",
        classes = @ConstructorResult(
                targetClass = AverageDrugDosageResultEntity.class,
                columns = {
                        @ColumnResult(name = Database.StoredProcedure.ResultColumn.TRIAL_ID, type = Integer.class),
                        @ColumnResult(name = Database.StoredProcedure.ResultColumn.TRIAL_NAME, type = String.class),
                        @ColumnResult(name = Database.StoredProcedure.ResultColumn.DRUG_ID, type = Integer.class),
                        @ColumnResult(name = Database.StoredProcedure.ResultColumn.DRUG_NAME, type = String.class),
                        @ColumnResult(name = Database.StoredProcedure.ResultColumn.DOSAGE_MEASUREMENT_TYPE, type = String.class),
                        @ColumnResult(name = Database.StoredProcedure.ResultColumn.AVERAGE_DOSAGE, type = BigDecimal.class),
                        @ColumnResult(name = Database.StoredProcedure.ResultColumn.PATIENT_COUNT, type = Integer.class)
                }
        )
)
@NamedStoredProcedureQuery(
        name = Database.StoredProcedure.Name.CALCULATE_AVERAGE_DOSAGE,
        procedureName = Database.StoredProcedure.Name.CALCULATE_AVERAGE_DOSAGE,
        resultClasses = AverageDrugDosageResultEntity.class,
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = Database.StoredProcedure.Parameters.TRIAL_ID, type = Integer.class)
        }
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AverageDrugDosageResultEntity {

    @Id
    @Column(name = Database.StoredProcedure.ResultColumn.TRIAL_ID)
    private Integer trialId;

    @Column(name = Database.StoredProcedure.ResultColumn.TRIAL_NAME)
    private String trialName;

    @Id
    @Column(name = Database.StoredProcedure.ResultColumn.DRUG_ID)
    private Integer drugId;

    @Column(name = Database.StoredProcedure.ResultColumn.DRUG_NAME)
    private String drugName;

    @Id
    @Column(name = Database.StoredProcedure.ResultColumn.DOSAGE_MEASUREMENT_TYPE)
    private String dosageMeasurementType;

    @Column(name = Database.StoredProcedure.ResultColumn.AVERAGE_DOSAGE)
    private BigDecimal averageDosage;

    @Column(name = Database.StoredProcedure.ResultColumn.PATIENT_COUNT)
    private Integer patientCount;

    @NoArgsConstructor
    @AllArgsConstructor
    public static class AverageDosageId implements Serializable {
        private Integer trialId;
        private Integer drugId;
        private String dosageMeasurementType;
    }

    public AverageDrugDosageResult toDomain() {
        return AverageDrugDosageResult.builder()
                .drugId(this.drugId)
                .drugName(this.drugName)
                .trialId(this.trialId)
                .trialName(this.trialName)
                .averageDosage(this.averageDosage != null ? this.averageDosage.setScale(2, RoundingMode.HALF_UP) : null)
                .dosageMeasurementType(ApplicationConstants.UnitsOfMeasurement.valueOf(this.dosageMeasurementType))
                .patientCount(this.patientCount)
                .build();
    }
}
