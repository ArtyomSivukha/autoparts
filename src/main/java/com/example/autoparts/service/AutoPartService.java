package com.example.autoparts.service;

import com.example.autoparts.advice.exception.AutoPartNotFoundException;
import com.example.autoparts.model.AutoPart;
import com.example.autoparts.model.User;
import com.example.autoparts.repository.AutoPartRepository;
import com.example.autoparts.repository.UserRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class AutoPartService {
    private final AutoPartRepository autoPartRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public List<AutoPart> getAll() {
        return autoPartRepository.findAll();
    }

    public AutoPart getById(Long id) {
        return autoPartRepository.findById(id).orElseThrow(() -> new AutoPartNotFoundException(id));
    }

    public List<AutoPart> create(AutoPart autoPart) {
        User user = userRepository.findByEmail(userService.getCurrent().getEmail());
        autoPart.setSupplier(user);
        user.getSuppliedParts().add(autoPart);
        return userRepository.save(user).getSuppliedParts();
    }

    public List<?> getPartsFromCurrentUser(){
        return userRepository.findById(userService.getCurrent().getId()).get().getSuppliedParts();
    }

    public List<?> getPartsBySupplierId(Long supplierId){
        return userRepository.findById(supplierId).get().getSuppliedParts();
    }
    public AutoPart deleteById(Long id) {
        AutoPart autoPart = autoPartRepository.findById(id).orElseThrow(() -> new AutoPartNotFoundException(id));
        autoPartRepository.deleteById(id);
        return autoPart;
    }

    public AutoPart editById(AutoPart autoPartChanged, Long id) {
        AutoPart preChangeAutoPart = autoPartRepository.findById(id).orElseThrow(() -> new AutoPartNotFoundException(id));

        if (autoPartChanged.getName() != null) {
            preChangeAutoPart.setName(autoPartChanged.getName());
        }
        if (autoPartChanged.getStockQuantity() != null) {
            preChangeAutoPart.setStockQuantity(autoPartChanged.getStockQuantity());
        }
        if (autoPartChanged.getPrice() != null) {
            preChangeAutoPart.setPrice(autoPartChanged.getPrice());
        }
        if (autoPartChanged.getDescription() != null) {
            preChangeAutoPart.setDescription(autoPartChanged.getDescription());
        }
        if (autoPartChanged.getCategory() != null) {
            preChangeAutoPart.setCategory(autoPartChanged.getCategory());
        }

        return autoPartRepository.save(preChangeAutoPart);
    }
}
