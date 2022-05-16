package com.team5.deliveryApi.repositories;

import com.team5.deliveryApi.models.Item;
import com.team5.deliveryApi.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item,Integer> {

   //@Query("update Item item set item.groceryItem = null where item.groceryItemId = ?1")
   //@Modifying
   //Query("delete * from Item where item.groceryItem.Id=?1")
   //Item removeItem(Integer groceryItemId);


}
