package com.example.autoparts.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    private String message;
    private LocalDateTime date;
    private boolean read;

    @ManyToOne
    @JoinColumn(name = "order_id")  // Указываем внешний ключ для связи с Order
    private Order order;

    @ManyToOne
    @JoinColumn(name = "bonus_id")  // Указываем внешний ключ для связи с Bonus
    private Bonus bonus;

    // Геттеры и сеттеры
}