package com.bosonit.virtualtravel.correo.infraestructure.kafka;

import com.bosonit.virtualtravel.correo.domain.Correo;
import com.bosonit.virtualtravel.correo.infraestructure.repository.ICorreoRepositoryJPA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.LocalDateTime;

// Clase para enviar mensajes

//@Component
@Slf4j
public class KafkaMessageProducer {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private ICorreoRepositoryJPA repositoryJPA;

    public void sendMessage(String topic, String message) {
//        if (topic == null || topic.trim().equals(""))
//            topic = topicName;

        // Creamos correo para guardar en BD
        Correo correo = new Correo();
        correo.setFecha(LocalDateTime.now());
        correo.setMensaje(message);
        repositoryJPA.save(correo);

        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, correo);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, Object> result) {
               // kafkaTemplate.send(future);
               log.info("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message=[" + message + "] due to : " + ex.getMessage());
            }
        });
    }
}