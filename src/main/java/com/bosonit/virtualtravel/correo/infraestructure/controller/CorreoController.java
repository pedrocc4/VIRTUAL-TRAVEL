package com.bosonit.virtualtravel.correo.infraestructure.controller;

import com.bosonit.virtualtravel.correo.infraestructure.kafka.KafkaMessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@RestController
@Slf4j
public class CorreoController {
    @Autowired
    private KafkaMessageProducer kafkaMessageProducer;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/add/{topic}")
    public ResponseEntity<Void> addIdCustomer(
            @PathVariable String topic,
            @RequestBody String body) {
        log.info("Intentando enviar mensaje: " + body + ", con topic: " + topic);
        kafkaMessageProducer.sendMessage(topic, body);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
