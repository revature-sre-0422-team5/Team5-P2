package com.team5.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class OrderItemController {
    
    @RequestMapping("/items/addItem")
    public ResponseEntity addItem(){
        return null;
    }

    @RequestMapping ("/items/updateItem")
    public ResponseEntity updateItem (){
        return null;
    }

}
