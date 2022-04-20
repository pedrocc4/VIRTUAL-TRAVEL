package com.bosonit.virtualtravel.autobus.infraestructure.controller;

import com.bosonit.virtualtravel.autobus.domain.Autobus;
import com.bosonit.virtualtravel.autobus.infraestructure.repository.IAutobusRepositoryJPA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class AutobusController {

    @Autowired
    private IAutobusRepositoryJPA repositoryJPA;

    @GetMapping("autobuses")
    public List<Autobus> getAutobuses() {
        log.info("Mostrando todos los autobuses...");
        return repositoryJPA.findAll();
    }
}
