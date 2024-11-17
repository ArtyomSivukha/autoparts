package com.example.autoparts.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    private AutoPart part;

    private int quantity;
    private BigDecimal price;

    public CartItem() {
    }

    public CartItem(BigDecimal price, int quantity, AutoPart part, Order order, Cart cart, Long cartItemId) {
        this.price = price;
        this.quantity = quantity;
        this.part = part;
        this.order = order;
        this.cart = cart;
        this.cartItemId = cartItemId;
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public AutoPart getPart() {
        return part;
    }

    public void setPart(AutoPart part) {
        this.part = part;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    // Геттеры и сеттеры
}