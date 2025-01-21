package com.app.service;

import com.app.entity.Slot;
import com.app.entity.SalesManager;
import com.app.model.AvailableSlotResponse;
import com.app.repository.SlotRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class SlotServiceTest {

    private final SlotRepository slotRepository = Mockito.mock(SlotRepository.class);
    private final SlotService slotService = new SlotService(slotRepository);

    @Test
    void testGetAvailableSlots_ValidInputs() {
        ZonedDateTime startTime = ZonedDateTime.of(2024, 5, 3, 10, 30, 0, 0, ZoneId.of("UTC"));
        SalesManager manager = new SalesManager();
        manager.setId(Long.parseLong("1"));
        manager.setLanguages(List.of("German"));
        manager.setProducts(List.of("SolarPanels"));
        manager.setCustomerRatings(List.of("Gold"));

        Slot slot = new Slot();
        slot.setStartDate(startTime);
        slot.setSalesManager(manager);

        Mockito.when(slotRepository.findAvailableSlotsWithinRange(any(), any()))
                .thenReturn(List.of(slot));

        List<AvailableSlotResponse> results = slotService.getAvailableSlots(
                LocalDate.of(2024, 5, 3),
                List.of("SolarPanels"),
                "German",
                "Gold"
        );

        assertEquals(1, results.size());
        assertEquals(startTime, results.get(0).getStartDate());
        assertEquals(1, results.get(0).getAvailableCount());
    }

    @Test
    void testGetAvailableSlots_NoValidSlots() {
        Mockito.when(slotRepository.findAvailableSlotsWithinRange(any(), any()))
                .thenReturn(Collections.emptyList());

        List<AvailableSlotResponse> results = slotService.getAvailableSlots(
                LocalDate.of(2024, 5, 3),
                List.of("SolarPanels"),
                "German",
                "Gold"
        );

        assertTrue(results.isEmpty());
    }

    @Test
    void testGetAvailableSlots_FilteringLogic() {
        ZonedDateTime startTime = ZonedDateTime.of(2024, 5, 3, 10, 30, 0, 0, ZoneId.of("UTC"));

        SalesManager manager1 = new SalesManager();
        manager1.setId(Long.parseLong("1"));
        manager1.setLanguages(List.of("English"));
        manager1.setProducts(List.of("Heatpumps"));
        manager1.setCustomerRatings(List.of("Silver"));

        SalesManager manager2 = new SalesManager();
        manager2.setId(Long.parseLong("2"));
        manager2.setLanguages(List.of("German"));
        manager2.setProducts(List.of("SolarPanels"));
        manager2.setCustomerRatings(List.of("Gold"));

        Slot slot1 = new Slot();
        slot1.setStartDate(startTime);
        slot1.setSalesManager(manager1);

        Slot slot2 = new Slot();
        slot2.setStartDate(startTime.plusMinutes(30));
        slot2.setSalesManager(manager2);

        Mockito.when(slotRepository.findAvailableSlotsWithinRange(any(), any()))
                .thenReturn(List.of(slot1, slot2));

        List<AvailableSlotResponse> results = slotService.getAvailableSlots(
                LocalDate.of(2024, 5, 3),
                List.of("SolarPanels"),
                "German",
                "Gold"
        );

        assertEquals(1, results.size());
        assertEquals(startTime.plusMinutes(30), results.get(0).getStartDate());
    }
}
