package com.isninfo.resafacile.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class InfoController {
    @Value("${resafacile.nom}")
    private String nom;
    @Value("${resafacile.version-serie}")
    private String versionSerie;
    @Value("${server.port}")
    private int port;

    //Get http://localhost:9090/info
    @GetMapping("/info")
    public Map<String, Object> info() {
        return Map.of(
                "application", nom,
                "serie", versionSerie,
                "port", port
        );
    }
}
