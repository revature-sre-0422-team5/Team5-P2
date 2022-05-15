package com.team5.deliveryApi.services;

import com.team5.deliveryApi.models.Shopper;
import com.team5.deliveryApi.repositories.ShopperRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShopperService {
    private ShopperRepository shopperRepository;

    public ShopperService(ShopperRepository shopperRepository) {
        super();
        this.shopperRepository = shopperRepository;
    }
    // Method that creates and saves shopper to the repository
    public boolean saveShopper(Shopper creatingShopper){
        shopperRepository.save(creatingShopper);
        return true;
    }
    // Method that returns the list of Shopper
    public List<Shopper> viewShopper(){
        return shopperRepository.findAll();
    }
    // To fetch shopper by Id
    public Optional<Shopper> getShopperById(int id){return shopperRepository.findById(id);
    }
    //For get mapping, fetching shopper by their Id.
    public ResponseEntity viewShopperById(int id){
        return ResponseEntity.ok(shopperRepository.findById(id).get());
    }
}
