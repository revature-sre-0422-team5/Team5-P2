package com.team5.deliveryApi.controllers;

import com.team5.deliveryApi.models.Shopper;
import com.team5.deliveryApi.dto.Status;
import com.team5.deliveryApi.repositories.ShopperRepository;
import com.team5.deliveryApi.services.ShopperService;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shopper")
public class ShopperController {
    @Autowired
    ShopperService shopperService;
    @Autowired
    ShopperRepository shopperRepository;

    @PostMapping("/login")
    public Status loginShopper(@Validated @RequestBody Shopper shopper){
        List<Shopper> shoppers = shopperRepository.findAll();
        for (Shopper other : shoppers){
            if (other.equals(shopper)){
                shopper.setLoggedIn(true);
                shopperRepository.save(shopper);
                return Status.SUCCESS;
            }
        } return Status.FAILURE;
    }

    @PostMapping("/create")
    public String createShopperAccount(@RequestBody Shopper creatingShopper){
        Logger logger = LoggerFactory.getLogger(ShopperController.class);
        boolean success = shopperService.saveShopper(creatingShopper);
        logger.info("Creating new Shopper");
        logger.info("creating shopper" + creatingShopper);
        if (success==true){
            return "Shopper Account Created Successfully";
            } else{
            return "Error in creating new Shopper Account";
        }
    }

    
    @PostMapping("/logout")
    public Status logShopperOut(@Validated @RequestBody Shopper shopper){
        List<Shopper> shoppers = shopperRepository.findAll();
        for (Shopper other : shoppers) {
            if (other.equals(shopper)) {
                shopper.setLoggedIn(false);
                shopperRepository.save(shopper);
                return Status.SUCCESS;
            }
        }
        return Status.FAILURE;
    }


    // Fetch Shopper by ID, passing ID as path variable in get mapping method
    @GetMapping("/{id}")
    public ResponseEntity viewShopperById(@PathVariable int id){return ResponseEntity.ok(shopperService.viewShopperById(id));}

    // Displays list of all shoppers by Get Mapping method
    @GetMapping("/all")
    public List<Shopper> viewAllShoppers(){return shopperService.viewShopper();}

}
