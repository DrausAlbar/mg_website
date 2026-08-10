package com.microgames.website.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerWeb {

    @GetMapping("/home")
    public String index()
    {
        return "index";
    }

   
    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

}
