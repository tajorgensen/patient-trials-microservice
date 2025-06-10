package com.tajorgensen.patienttrials.adapter.gateway.repository.entity;


import com.tajorgensen.patienttrials.common.constants.ApplicationConstants.Database;
import com.tajorgensen.patienttrials.domain.model.Trial;
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

@Entity
@Table(name = Database.TableName.TRIAL, schema = Database.Schema.DBO)
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class TrialEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = Database.ColumnName.ID)
    private Long id;

    @Column(name = Database.ColumnName.NAME)
    private String name;

    @Column(name = Database.ColumnName.START_DATE)
    private LocalDate startDate;

    @Column(name = Database.ColumnName.END_DATE)
    private LocalDate endDate;

    @Column(name = Database.ColumnName.PROTOCOL_DESCRIPTION)
    private String protocolDescription;

    public Trial toDomain() {
        return Trial.builder()
                .id(this.id)
                .name(this.name)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .protocolDescription(this.protocolDescription)
                .build();
    }

    public static TrialEntity of(Trial domain) {
        if (domain == null) {
            return null;
        }

        return TrialEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .protocolDescription(domain.getProtocolDescription())
                .build();
    }

}
