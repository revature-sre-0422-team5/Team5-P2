package com.team5.deliveryApi.repositories;

import com.team5.deliveryApi.models.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GroceryItemRepository extends JpaRepository<GroceryItem, Integer> {

}
