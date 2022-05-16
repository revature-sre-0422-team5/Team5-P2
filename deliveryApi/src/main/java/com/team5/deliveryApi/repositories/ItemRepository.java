package com.team5.deliveryApi.repositories;

import com.team5.deliveryApi.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item,Integer> {
    //void delete(Optional<Item> cartItem);
}
