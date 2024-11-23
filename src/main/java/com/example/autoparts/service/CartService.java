package com.example.autoparts.service;

import com.example.autoparts.model.AutoPart;
import com.example.autoparts.model.Cart;
import com.example.autoparts.model.CartItem;
import com.example.autoparts.model.User;
import com.example.autoparts.repository.AutoPartRepository;
import com.example.autoparts.repository.CartRepository;
import com.example.autoparts.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class CartService {
    private final CartRepository cartRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final AutoPartRepository autoPartRepository;

    public List<CartItem> getCartItemsFromCurrent() {
        return userService.getCurrent().getCart().getItems();
    }

    public Cart addCartItem(Long autoPartId) {
        CartItem cartItem = new CartItem();
        Cart cart = userService.getCurrent().getCart();
        AutoPart autoPart = autoPartRepository.findById(autoPartId).get();
        cartItem.setCart(cart);
        cartItem.setPart(autoPart);
        cartItem.setQuantity(1);
        cartItem.setPrice(BigDecimal.valueOf(autoPart.getPrice()));
        cart.getItems().add(cartItem);
        return cartRepository.save(cart);
    }

    public CartItem deleteCartItem(Long autoPartID) {
        Cart cart = userService.getCurrent().getCart();

        CartItem itemToDelete = cart.getItems().stream()
                .filter(item -> item.getPart().getId().equals(autoPartID))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("В корзине отсутствует"));
        cart.getItems().remove(itemToDelete);
        cartRepository.save(cart);
        return itemToDelete;
    }
}