package com.tajorgensen.patienttrials.adapter.gateway.client;

import com.tajorgensen.patienttrials.adapter.gateway.repository.AdverseEventRepository;
import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.AdverseEventEntity;
import com.tajorgensen.patienttrials.common.exception.ErrorConstants;
import com.tajorgensen.patienttrials.common.exception.ResourceNotFoundException;
import com.tajorgensen.patienttrials.domain.model.AdverseEvent;
import com.tajorgensen.patienttrials.utils.AdverseEventTestUtils;
import com.tajorgensen.patienttrials.utils.TrialTestUtils;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdverseEventClientTest {

    @Mock
    private AdverseEventRepository adverseEventRepository;

    @InjectMocks
    private AdverseEventClient adverseEventClient;

    @Test
    void testCreateAdverseEvent() {
        AdverseEventEntity adverseEventEntity = AdverseEventTestUtils.createEntity();

        when(adverseEventRepository.save(eq(adverseEventEntity))).thenReturn(adverseEventEntity);

        AdverseEvent adverseEvent = AdverseEventTestUtils.createDomain();

        AdverseEvent actualResult = adverseEventClient.createAdverseEvent(adverseEvent);

        AdverseEventTestUtils.assertDomainModelsEqual(adverseEvent, actualResult);
    }

    @Test
    void testGetAdverseEventById() {
        AdverseEventEntity adverseEventEntity = AdverseEventTestUtils.createEntity();

        when(adverseEventRepository.findById(eq(AdverseEventTestUtils.ID))).thenReturn(Optional.of(adverseEventEntity));

        AdverseEvent expectedResult = AdverseEventTestUtils.createDomain();

        AdverseEvent actualResult = adverseEventClient.getAdverseEventById(AdverseEventTestUtils.ID);

        AdverseEventTestUtils.assertDomainModelsEqual(expectedResult, actualResult);
    }


    @Test
    void testGetAdverseEventById_DoesNotExist() {
        when(adverseEventRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> adverseEventClient.getAdverseEventById(AdverseEventTestUtils.ID));

        assertEquals(ErrorConstants.AdverseEventErrorCode.GET_ID_NOT_FOUND.getCode(), ex.getErrorDetails().getErrorCode());
    }

    @Test
    void testGetAdverseEventsByTrialId() {
        AdverseEventEntity adverseEventEntity = AdverseEventTestUtils.createEntity();

        when(adverseEventRepository.findByTrialId(eq(TrialTestUtils.ID))).thenReturn(List.of(adverseEventEntity));

        AdverseEvent expectedResult = AdverseEventTestUtils.createDomain();

        List<AdverseEvent> actualResultList = adverseEventClient.getAdverseEventsByTrialId(TrialTestUtils.ID);

        assertAll(
                () -> assertFalse(actualResultList.isEmpty()),
                () -> assertEquals(1, actualResultList.size()),
                () -> AdverseEventTestUtils.assertDomainModelsEqual(expectedResult, actualResultList.get(0))
        );
    }


    @Test
    void testGetAdverseEventsByTrialId_DoesNotExist() {
        when(adverseEventRepository.findByTrialId(anyLong())).thenReturn(Collections.emptyList());

        List<AdverseEvent> adverseEventList = adverseEventClient.getAdverseEventsByTrialId(TrialTestUtils.ID);

        assertAll(
                () -> assertNotNull(adverseEventList),
                () -> assertTrue(adverseEventList.isEmpty())
        );
    }

    @Test
    void testUpdateAdverseEvent() {
        AdverseEventEntity adverseEventEntity = AdverseEventTestUtils.createEntity();

        when(adverseEventRepository.saveIfExists(eq(adverseEventEntity))).thenReturn(adverseEventEntity);

        AdverseEvent adverseEvent = AdverseEventTestUtils.createDomain();

        AdverseEvent actualResult = adverseEventClient.updateAdverseEvent(adverseEvent);

        AdverseEventTestUtils.assertDomainModelsEqual(adverseEvent, actualResult);
    }

    @Test
    void testUpdateAdverseEvent_DoesNotExist() {
        AdverseEventEntity adverseEventEntity = AdverseEventTestUtils.createEntity();
        when(adverseEventRepository.saveIfExists(eq(adverseEventEntity))).thenThrow(new JpaObjectRetrievalFailureException(new EntityNotFoundException()));

        AdverseEvent adverseEvent = AdverseEventTestUtils.createDomain();

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> adverseEventClient.updateAdverseEvent(adverseEvent));

        assertEquals(ErrorConstants.AdverseEventErrorCode.UPDATE_ID_NOT_FOUND.getCode(), ex.getErrorDetails().getErrorCode());
    }

    @Test
    void testDeleteAdverseEvent() {
        assertDoesNotThrow(() -> adverseEventClient.deleteAdverseEventById(AdverseEventTestUtils.ID));
        verify(adverseEventRepository, times(1)).deleteById(AdverseEventTestUtils.ID);
    }

    @Test
    void testDeleteAdverseEvent_DoesNotExist() {
        doNothing().when(adverseEventRepository).deleteById(eq(AdverseEventTestUtils.ID));
        assertDoesNotThrow(() -> adverseEventClient.deleteAdverseEventById(AdverseEventTestUtils.ID));
    }

}