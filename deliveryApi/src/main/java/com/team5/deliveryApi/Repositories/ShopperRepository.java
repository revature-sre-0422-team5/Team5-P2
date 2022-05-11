package com.team5.deliveryApi.Repositories;
import com.team5.deliveryApi.Models.Shopper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopperRepository extends JpaRepository<Shopper,Integer> {
    Shopper save(Shopper creatingShopper);

    @Override
    Optional<Shopper> findById(Integer id);
}
