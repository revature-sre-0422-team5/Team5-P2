package com.team5.deliveryApi.repositories;
import com.team5.deliveryApi.models.Shopper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopperRepository extends JpaRepository<Shopper,Integer> {
    Shopper save(Shopper creatingShopper);

    @Override
    Optional<Shopper> findById(Integer id);
}
