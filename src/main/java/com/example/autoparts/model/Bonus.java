package com.example.autoparts.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Bonus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bonusId;

    private int bonusPoints;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "bonus", cascade = CascadeType.ALL)
    private List<Notification> notifications;

    // Геттеры и сеттеры
}