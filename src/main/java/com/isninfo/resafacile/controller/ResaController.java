package com.isninfo.resafacile.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResaController {

    @GetMapping("/")
    public String salutation(){
        return "Bonjour Je viens du controleur !!!";
    }

    @GetMapping("salut/raym")
    public String salutRaym(@RequestParam(defaultValue = "invité")String nom){
        return "Bonjour " + nom +" !";
    }
}
