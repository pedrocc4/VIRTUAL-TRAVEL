package com.bosonit.virtualtravel.utils.kafka;

import com.bosonit.virtualtravel.correo.domain.Correo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

// Clase para enviar mensajes

@Component
@Slf4j
public class KafkaMessageProducer {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(String topic, Correo correo) {

        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, correo);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, Object> result) {
                kafkaTemplate.send((Message<?>) future);
                log.info("Sent message=[" + correo.getMensaje() + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message=[" + correo.getMensaje() + "] due to : " + ex.getMessage());
            }
        });
    }
}