package com.app.entity;

import com.app.util.PostgreSQLStringListConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "sales_managers")
@Data
public class SalesManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "languages")
    @Convert(converter = PostgreSQLStringListConverter.class)
    private List<String> languages;

    @Column(name = "products")
    @Convert(converter = PostgreSQLStringListConverter.class)
    private List<String> products;

    @Column(name = "customer_ratings")
    @Convert(converter = PostgreSQLStringListConverter.class)
    private List<String> customerRatings;

}

