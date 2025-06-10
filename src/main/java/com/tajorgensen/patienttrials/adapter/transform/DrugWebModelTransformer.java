package com.tajorgensen.patienttrials.adapter.transform;

import com.tajorgensen.patienttrials.adapter.model.DrugWebModel;
import com.tajorgensen.patienttrials.domain.model.Drug;

public class DrugWebModelTransformer {

    public static DrugWebModel fromDomain(Drug domain) {
        if (domain == null) {
            return null;
        }

        return DrugWebModel.builder()
                .id(domain.getId())
                .name(domain.getName())
                .chemicalFormula(domain.getChemicalFormula())
                .manufacturer(domain.getManufacturer())
                .dosage(domain.getDosage())
                .dosageMeasurementType(domain.getDosageMeasurementType())
                .build();
    }

    public static Drug toDomain(DrugWebModel webModel) {
        if (webModel == null) {
            return null;
        }

        return Drug.builder()
                .id(webModel.getId())
                .name(webModel.getName())
                .chemicalFormula(webModel.getChemicalFormula())
                .manufacturer(webModel.getManufacturer())
                .dosage(webModel.getDosage())
                .dosageMeasurementType(webModel.getDosageMeasurementType())
                .build();
    }

}
