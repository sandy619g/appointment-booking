package com.app.controller;

import com.app.model.AvailableSlotResponse;
import com.app.model.SlotQueryRequest;
import com.app.service.SlotService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/calendar")
public class SlotController {

    private final SlotService slotService;

    public SlotController(SlotService slotService) {
        this.slotService = slotService;
    }

    @PostMapping("/query")
    public List<AvailableSlotResponse> querySlots(@RequestBody SlotQueryRequest request) {
        LocalDate date = LocalDate.parse(request.getDate());
        return slotService.getAvailableSlots(date, request.getProducts(), request.getLanguage(), request.getRating());
    }
}

