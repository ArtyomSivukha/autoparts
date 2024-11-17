package com.example.autoparts.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "auto_part")
public class AutoPart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partId;

    private String partName;
    private String description;
    private Double price;
    private Integer stockQuantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "supplier_user_id")
    private User supplier;

    // Getters, Setters, Constructors
}