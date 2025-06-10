package com.tajorgensen.patienttrials.adapter.gateway.repository.entity;

import com.tajorgensen.patienttrials.common.constants.ApplicationConstants.Database;
import com.tajorgensen.patienttrials.domain.model.Drug;
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

@Entity
@Table(name = Database.TableName.DRUG, schema = Database.Schema.DBO)
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class DrugEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = Database.ColumnName.ID)
    private Long id;

    @Column(name = Database.ColumnName.NAME)
    private String name;

    @Column(name = Database.ColumnName.CHEMICAL_FORMULA)
    private String chemicalFormula;

    @Column(name = Database.ColumnName.MANUFACTURER)
    private String manufacturer;

    public Drug toDomain() {
        return Drug.builder()
                .id(this.id)
                .name(this.name)
                .chemicalFormula(this.chemicalFormula)
                .manufacturer(this.manufacturer)
                .build();
    }

    public static DrugEntity of(Drug domain) {
        if (domain == null) {
            return null;
        }

        return DrugEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .chemicalFormula(domain.getChemicalFormula())
                .manufacturer(domain.getManufacturer())
                .build();
    }

}
