package com.app.model;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class AvailableSlotResponse {
    private ZonedDateTime startDate;
    private int availableCount;

    public AvailableSlotResponse(ZonedDateTime startDate, int availableCount) {
        this.startDate = startDate;
        this.availableCount = availableCount;
    }

    public AvailableSlotResponse() {

    }
}

