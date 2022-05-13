package com.team5.deliveryApi.Repositories;

import com.team5.deliveryApi.Models.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroceryItemRepository extends JpaRepository<GroceryItem, Integer> {
}
