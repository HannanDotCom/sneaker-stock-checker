package com.hannandot.nikestockchecker.repo;

import com.hannandot.nikestockchecker.model.DesiredItem;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DesiredItemRepo extends JpaRepository<DesiredItem,Long> {
    void deleteDesiredItemByItemID(Long Id);

    Optional<DesiredItem> findDesiredItemByItemID(Long Id);
}
