# MANUAL DE USUARIO
## DOCKER - Instalación automática 
En primer lugar clonaremos el repositorio en local, posteriormente abriremos una consola con la ruta asociada a la raíz de nuestra descarga, finalmente introducimos el siguiente comando:  
```
docker compose up
```

Este comando ejecutará el fichero _docker-compose.yml_ que construirá las imágenes necesarias para la puesta en marcha.  

Una vez ejecutado y tras esperar, ya podemos disfrutar de Virtual-Travel.
## Instalación manual
Para lanzar la aplicación en su plenitud de forma manual, será necesario seguir los siguientes pasos:
- **Instalar kafka:** descargaremos de la [siguiente página](https://www.apache.org/dyn/closer.cgi?path=/kafka/3.1.0/kafka_2.13-3.1.0.tgz)
y tras descomprimir situaremos en la raíz de windows.
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


      
