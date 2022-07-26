package com.example.shexa_security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/login")
public class TemplateController {

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }
}
