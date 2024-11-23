package com.example.autoparts.controller;

import com.example.autoparts.model.AutoPart;
import com.example.autoparts.model.User;
import com.example.autoparts.service.AutoPartService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("/api/autoparts")
public class AutoPartController {
    private final AutoPartService autoPartService;

    @GetMapping
    public ResponseEntity<?> getAllAutoParts() {
        return new ResponseEntity<>(autoPartService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return new ResponseEntity<>(autoPartService.getById(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(autoPartService.deleteById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody AutoPart autoPart) {
        return new ResponseEntity<>(autoPartService.create(autoPart), HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editById(@PathVariable Long id, @RequestBody AutoPart autoPartToChange){
        return new ResponseEntity<>(autoPartService.editById(autoPartToChange, id), HttpStatus.OK);
    }
}
