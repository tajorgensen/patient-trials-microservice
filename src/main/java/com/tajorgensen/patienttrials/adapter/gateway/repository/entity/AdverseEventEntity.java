package com.tajorgensen.patienttrials.adapter.gateway.repository.entity;

import com.tajorgensen.patienttrials.common.constants.ApplicationConstants;
import com.tajorgensen.patienttrials.common.constants.ApplicationConstants.Database;
import com.tajorgensen.patienttrials.domain.model.AdverseEvent;
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
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = Database.TableName.ADVERSE_EVENT, schema = Database.Schema.DBO)
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AdverseEventEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = Database.ColumnName.ID)
    private Long id;

    @Column(name = Database.ColumnName.PATIENT_ID)
    private Long patientId;

    @Column(name = Database.ColumnName.TRIAL_ID)
    private Long trialId;

    @Column(name = Database.ColumnName.EVENT_DESCRIPTION)
    private String eventDescription;

    @Column(name = Database.ColumnName.EVENT_SEVERITY)
    private String eventSeverity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Database.ColumnName.PATIENT_ID, insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private PatientEntity patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Database.ColumnName.TRIAL_ID, insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private TrialEntity trial;

    public AdverseEvent toDomain() {
        return AdverseEvent.builder()
                .id(this.id)
                .patientId(this.patientId)
                .trialId(this.trialId)
                .eventDescription(this.eventDescription)
                .eventSeverity(ApplicationConstants.EventSeverity.valueOf(this.eventSeverity))
                .build();
    }

    public static AdverseEventEntity of(AdverseEvent domain) {
        if (domain == null) {
            return null;
        }

        return AdverseEventEntity.builder()
                .id(domain.getId())
                .patientId(domain.getPatientId())
                .trialId(domain.getTrialId())
                .eventDescription(domain.getEventDescription())
                .eventSeverity(domain.getEventSeverity().name())
                .build();
    }

}
