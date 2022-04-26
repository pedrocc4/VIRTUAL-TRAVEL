package com.bosonit.virtualtravel.autobus.infraestructure.controller;

import com.bosonit.virtualtravel.autobus.infraestructure.controller.dto.input.AutobusInputDTO;
import com.bosonit.virtualtravel.autobus.infraestructure.controller.dto.output.AutobusFullOutputDTO;
import com.bosonit.virtualtravel.autobus.service.IAutobusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/v0")
public class AutobusController {

    @Autowired
    private IAutobusService service;

    @GetMapping("autobus/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AutobusFullOutputDTO> getAutobus(
            @PathVariable String id) {
        log.info("Intentando recuperar autobus con id: " + id);
        return ResponseEntity.ok().body(service.getAutobus(id));
    }

    @PostMapping("autobus")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AutobusFullOutputDTO> addAutobus(
            @RequestBody AutobusInputDTO autobusInputDTO) {
        log.info("Intentando crear autobus con datos: " + autobusInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addAutobus(autobusInputDTO));
    }

    @PutMapping("autobus/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AutobusFullOutputDTO> actAutobuses(
            @PathVariable String id,
            @RequestBody AutobusInputDTO autobusInputDTO) {
        log.info("Intentando actualizar autobus con id: " + id +
                ", con datos: " + autobusInputDTO);
        return ResponseEntity.ok().body(service.actAutobus(id, autobusInputDTO));
    }

    @DeleteMapping("autobus/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> delAutobus(
            @PathVariable String id) {
        log.info("Intentando borrar autobus con id: " + id);
        service.delAutobus(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("autobuses")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<AutobusFullOutputDTO>> getAutobuses() {
        log.info("Mostrando todos los autobuses...");
        return ResponseEntity.ok().body(service.getAutobuses());
    }
}
