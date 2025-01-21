package com.app.service;

import com.app.entity.Slot;
import com.app.model.AvailableSlotResponse;
import com.app.repository.SlotRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SlotService {

    private final SlotRepository slotRepository;

    public SlotService(SlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    public List<AvailableSlotResponse> getAvailableSlots(LocalDate date, List<String> products, String language, String rating) {
        ZonedDateTime startOfDay = date.atStartOfDay(ZoneId.of("UTC"));
        ZonedDateTime endOfDay = startOfDay.plusDays(1);

        List<Slot> validSlots = slotRepository.findAvailableSlotsWithinRange(startOfDay, endOfDay).stream()
                .filter(slot -> slot.getSalesManager().getLanguages().contains(language))
                .filter(slot -> slot.getSalesManager().getProducts().containsAll(products))
                .filter(slot -> slot.getSalesManager().getCustomerRatings().contains(rating))
                .collect(Collectors.toList());

        Map<ZonedDateTime, Set<Integer>> groupedAgents = new TreeMap<>();

        for (Slot slot : validSlots) {
            ZonedDateTime timeSlot = slot.getStartDate();
            int agentId = slot.getSalesManager().getId().intValue();

            groupedAgents.computeIfAbsent(timeSlot, k -> new HashSet<>()).add(agentId);
        }

        return groupedAgents.entrySet().stream()
                .map(entry ->
                        new AvailableSlotResponse(entry.getKey(), entry.getValue().size()))
                .collect(Collectors.toList());
    }



}
