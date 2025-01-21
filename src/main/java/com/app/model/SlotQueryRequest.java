package com.app.model;

import lombok.Data;

import java.util.List;

@Data
public class SlotQueryRequest {
    private String date;
    private List<String> products;
    private String language;
    private String rating;
}
