package com.dealpro.dealpro_agencia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index"; // templates/index.html
    }

    @GetMapping("/portafolio")
    public String portafolio() {
        return "portafolio"; // templates/portafolio.html
    }
    
    @GetMapping("/servicios")
    public String servicios() {
        return "servicios"; // servicios.html
    }
}