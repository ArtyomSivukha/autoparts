package com.example.autoparts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.autoparts.model.AutoPart;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoPartRepository extends JpaRepository<AutoPart, Long> {
}
