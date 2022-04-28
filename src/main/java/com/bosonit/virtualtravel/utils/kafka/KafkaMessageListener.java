package com.bosonit.virtualtravel.utils.kafka;

import com.bosonit.virtualtravel.correo.domain.Correo;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.KafkaClient;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaMessageListener {

    @KafkaListener(topics = "${topic}", groupId = "${group}")
    public void listenTopic(Correo correo)  {
        log.info("Recieved Message of topic in  listener: " + correo.getMensaje());
    }

}
