package com.team5.deliveryApi.repositories;

import com.team5.deliveryApi.models.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface GroceryItemRepository extends JpaRepository<GroceryItem, Integer> {

}
