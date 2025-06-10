package com.tajorgensen.patienttrials.adapter.transform;

import com.tajorgensen.patienttrials.adapter.model.DrugWebModel;
import com.tajorgensen.patienttrials.domain.model.Drug;
import com.tajorgensen.patienttrials.utils.DrugTestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class DrugWebModelTransformerTest {

    @Test
    void testFromDomainNull() {
        assertNull(DrugWebModelTransformer.fromDomain(null));
    }

    @Test
    void testFromDomainEmpty() {
        Drug domain = new Drug();
        DrugWebModel expected = new DrugWebModel();

        DrugWebModel actual = DrugWebModelTransformer.fromDomain(domain);

        DrugTestUtils.assertWebModelsEqual(expected, actual);
    }

    @Test
    void testFromDomain() {
        Drug domain = DrugTestUtils.createDomain();
        DrugWebModel expected = DrugTestUtils.createWebModel();

        DrugWebModel actual = DrugWebModelTransformer.fromDomain(domain);

        DrugTestUtils.assertWebModelsEqual(expected, actual);
    }

    @Test
    void testToDomainNull() {
        assertNull(DrugWebModelTransformer.toDomain(null));
    }

    @Test
    void testToDomainEmpty() {
        DrugWebModel webModel = new DrugWebModel();
        Drug expected = new Drug();

        Drug actual = DrugWebModelTransformer.toDomain(webModel);

        DrugTestUtils.assertDomainModelsEqual(expected, actual);
    }

    @Test
    void testToDomain() {
        DrugWebModel webModel = DrugTestUtils.createWebModel();
        Drug expected = DrugTestUtils.createDomain();

        Drug actual = DrugWebModelTransformer.toDomain(webModel);

        DrugTestUtils.assertDomainModelsEqual(expected, actual);
    }
}
