package com.bosonit.virtualtravel.correo.infraestructure.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

//@Component
@Slf4j
public class KafkaMessageListener {

    @KafkaListener
    public void listenTopic1(String message) {
        log.info("Recieved Message of topic1 in  listener: " + message);
    }

    @KafkaListener(topics = "${message.topic.name2:kafkatopic2}", groupId = "${message.group.name:kafkagroup}")
    public void listenTopic2(String message) {
        log.info("Recieved Message of topic2 in  listener: " + message);
    }

}
