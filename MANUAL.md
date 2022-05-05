# MANUAL DE USUARIO
## Instalación
Clonar repositorio en local.  
*Por implementar*: docker compose
## Puesta en marcha
Para lanzar la aplicación en su plenitud, será necesario seguir los siguientes pasos:
- **Ejecutar Kafka:** para ello lanzaremos en primer lugar zookeeper con:   


-      zookeeper-server-start.bat C:\kafka_2.13-3.1.0\config\zookeeper.properties  

posteriormente el servicio principal de kafka con:  


-      kafka-server-start.bat C:\kafka_2.13-3.1.0\config\server.properties

- **Eureka y gateway**: lanzaremos la aplicación de eureka localizada en la raíz y finalmente busBalancer, igualmente en la raíz.
- **BackWeb**: una vez realizados todos los pasos anteriores podríamos realizar peticiones a la siguiente ruta: _localhost:8080/api/v0_ 

## Información adicional
Comentar que en esa misma ruta en nuestro navegador tenemos la opción de **consultar** y hacer una **reserva** para un viaje en concreto.

Actualmente hay 3 viajes disponibles en la base de datos, pero están caducados, por lo tanto lo ideal sería crear alguno antes.

En la ruta _localhost:8080/swagger-uihtml_ tenemos documentación  con Swagger

## Docker

Para lanzar kafka crearemos el siguiente docker-compose.yml que lanzará kafka y zookeeper:  
```  
version: '3'
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:7.0.1
    container_name: zookeeper
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000

  broker:
    image: confluentinc/cp-kafka:7.0.1
    container_name: broker
    ports:
    # To learn about configuring Kafka for access across networks see
    # https://www.confluent.io/blog/kafka-client-cannot-connect-to-broker-on-aws-on-docker-etc/
      - "9092:9092"
    depends_on:
      - zookeeper
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: 'zookeeper:2181'
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_INTERNAL:PLAINTEXT
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092,PLAINTEXT_INTERNAL://broker:29092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_TRANSACTION_STATE_LOG_MIN_ISR: 1
      KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: 1
      ``` 


      
