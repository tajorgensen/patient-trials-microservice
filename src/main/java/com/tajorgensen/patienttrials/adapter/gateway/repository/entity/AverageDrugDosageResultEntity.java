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
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
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
                        @ColumnResult(name = "trial_id", type = Integer.class),
                        @ColumnResult(name = "trial_name", type = String.class),
                        @ColumnResult(name = "drug_id", type = Integer.class),
                        @ColumnResult(name = "drug_name", type = String.class),
                        @ColumnResult(name = "dosage_measurement_type", type = String.class),
                        @ColumnResult(name = "average_dosage", type = BigDecimal.class),
                        @ColumnResult(name = "patient_count", type = Long.class)
                }
        )
)
@NamedNativeQuery(
        name = "AverageDrugDosageResultEntity.calculateAverageDosage",
        query = "SELECT * FROM dbo.CalculateAverageDosage(:trialId)",
        resultSetMapping = "AverageDosageMapping"
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AverageDrugDosageResultEntity {

    @Id
    @Column(name = "trial_id")
    private Integer trialId;

    @Column(name = "trial_name")
    private String trialName;

    @Id
    @Column(name = "drug_id")
    private Integer drugId;

    @Column(name = "drug_name")
    private String drugName;

    @Id
    @Column(name = "dosage_measurement_type")
    private String dosageMeasurementType;

    @Column(name = "average_dosage")
    private BigDecimal averageDosage;

    @Column(name = "patient_count")
    private Long patientCount;

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
                .patientCount(this.patientCount != null ? this.patientCount.intValue() : null)
                .build();
    }
}