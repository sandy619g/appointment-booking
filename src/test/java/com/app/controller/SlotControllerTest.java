package com.app.controller;

import com.app.model.AvailableSlotResponse;
import com.app.model.SlotQueryRequest;
import com.app.service.SlotService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SlotControllerTest {

    private final SlotService slotService = Mockito.mock(SlotService.class);
    private final SlotController slotController = new SlotController(slotService);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(slotController).build();

    @Test
    void testQuerySlots_ValidRequest() throws Exception {
        List<AvailableSlotResponse> mockResponse = List.of(
                new AvailableSlotResponse(ZonedDateTime.parse("2024-05-03T10:30:00Z"), 1)
        );
        Mockito.when(slotService.getAvailableSlots(any(), any(), any(), any())).thenReturn(mockResponse);

        String requestJson = """
            {
                "date": "2024-05-03",
                "products": ["SolarPanels"],
                "language": "German",
                "rating": "Gold"
            }
        """;

        mockMvc.perform(post("/calendar/query")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }


    @Test
    void testQuerySlots_NoResults() throws Exception {
        Mockito.when(slotService.getAvailableSlots(any(), any(), any(), any())).thenReturn(Collections.emptyList());

        String requestJson = """
            {
                "date": "2024-05-03",
                "products": ["NonExistentProduct"],
                "language": "UnknownLanguage",
                "rating": "Gold"
            }
        """;

        mockMvc.perform(post("/calendar/query")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
