# MANUAL DE USUARIO
## Instalación
Clonar repositorio en local.  
*Por implementar*: docker compose
## Puesta en marcha
Para lanzar la aplicación en su plenitud, será necesario seguir los siguientes pasos:
- **Ejecutar Kafka:** para ello lanzaremos en primer lugar zookeeper con:  
  
    
    zookeeper-server-start.bat C:\kafka_2.13-3.1.0\config\zookeeper.properties  

posteriormente el servicio principal de kafka con:  

    kafka-server-start.bat C:\kafka_2.13-3.1.0\config\server.properties

- **Eureka y gateway**: lanzaremos la aplicación de eureka localizada en la raíz y finalmente busBalancer, igualmente en la raíz.
- **BackWeb**: una vez realizados todos los pasos anteriores podríamos realizar peticiones a la siguiente ruta: _localhost:8080/api/v0_ 

Comentar que en esa misma ruta en nuestro navegador tenemos la opción de **consultar** y hacer una **reserva** para un viaje en concreto.

Actualmente hay 3 viajes disponibles en la base de datos, pero están caducados, por lo tanto lo ideal sería crear alguno antes.