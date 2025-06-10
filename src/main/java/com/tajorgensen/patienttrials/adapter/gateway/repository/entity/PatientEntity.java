package com.tajorgensen.patienttrials.adapter.gateway.repository.entity;


import com.tajorgensen.patienttrials.common.constants.ApplicationConstants;
import com.tajorgensen.patienttrials.common.constants.ApplicationConstants.Database;
import com.tajorgensen.patienttrials.domain.model.Patient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;

@Entity
@Table(name = Database.TableName.PATIENT, schema = Database.Schema.DBO)
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PatientEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = Database.ColumnName.ID)
    private Long id;

    @Column(name = Database.ColumnName.NAME)
    private String name;

    @Column(name = Database.ColumnName.DATE_OF_BIRTH)
    private LocalDate dateOfBirth;

    @Column(name = Database.ColumnName.GENDER)
    private Character gender;

    @Column(name = Database.ColumnName.MEDICAL_HISTORY)
    private String medicalHistory;

    public Patient toDomain() {
        return Patient.builder()
                .id(this.id)
                .name(this.name)
                .dateOfBirth(this.dateOfBirth)
                .gender(ApplicationConstants.Gender.findByCode(this.gender))
                .medicalHistory(this.medicalHistory)
                .build();
    }

    public static PatientEntity of(Patient domain) {
        if (domain == null) {
            return null;
        }

        return PatientEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .dateOfBirth(domain.getDateOfBirth())
                .gender(Optional.ofNullable(domain.getGender()).map(ApplicationConstants.Gender::getCode).orElse(null))
                .medicalHistory(domain.getMedicalHistory())
                .build();
    }

}
