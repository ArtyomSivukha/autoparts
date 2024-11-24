package com.example.autoparts.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "auto_part")
public class AutoPart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Float price;
    private Integer stockQuantity;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "supplier_user_id")
    private User supplier;
}