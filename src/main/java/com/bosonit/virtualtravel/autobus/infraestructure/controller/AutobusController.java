package com.bosonit.virtualtravel.autobus.infraestructure.controller;

import com.bosonit.virtualtravel.autobus.domain.Autobus;
import com.bosonit.virtualtravel.autobus.infraestructure.repository.IAutobusRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AutobusController {
    @Autowired
    private IAutobusRepositoryJPA repositoryJPA;

    @GetMapping("autobuses")
    public List<Autobus> getAutobuses() {
        return repositoryJPA.findAll();
    }
}
