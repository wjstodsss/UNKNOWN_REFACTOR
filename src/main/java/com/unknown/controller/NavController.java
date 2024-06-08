package com.unknown.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavController {

    @GetMapping("/expressDelivery")
    public String expressDelivery() {
        return "expressDelivery";
    }
    
    @GetMapping("/orangeMembers")
    public String orangeMembers() {
        return "orangeMembers";
    }
    
    @GetMapping("/event")
    public String event() {
        return "event";
    }
    
    @GetMapping("/manboki")
    public String manboki() {
        return "manboki";
    }
  
}
