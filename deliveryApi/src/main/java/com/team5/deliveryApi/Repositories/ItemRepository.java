package com.team5.deliveryApi.Repositories;

import com.team5.deliveryApi.Models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Integer> {
}
