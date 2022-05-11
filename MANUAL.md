# MANUAL DE USUARIO

## DOCKER - Instalación automática

En primer lugar clonaremos el repositorio en local, posteriormente abriremos una consola con la ruta asociada a la raíz
de nuestra descarga, finalmente introducimos el siguiente comando:

```
docker compose up
```
Comentar previamente que sería necesario introducir por variables de entorno en el docker compose el correo y contraseña
correspondiente para poder enviar correos.

Este comando ejecutará el fichero _docker-compose.yml_ que construirá las imágenes necesarias para la puesta en marcha.

Una vez ejecutado y tras esperar, ya podemos disfrutar de Virtual-Travel siguiendo el [manual de uso](#uso)

## Instalación manual

Para lanzar la aplicación en su plenitud de forma manual, será necesario seguir los siguientes pasos:

- **Instalar kafka:** descargaremos de
  la [siguiente página](https://www.apache.org/dyn/closer.cgi?path=/kafka/3.1.0/kafka_2.13-3.1.0.tgz)
  y tras descomprimir situaremos en la raíz de windows.
- **Ejecutar Kafka:** para ello lanzaremos en primer lugar zookeeper con:


-      zookeeper-server-start.bat C:\kafka_2.13-3.1.0\config\zookeeper.properties  

posteriormente el servicio principal de kafka con:

-      kafka-server-start.bat C:\kafka_2.13-3.1.0\config\server.properties

- **Eureka y gateway**: lanzaremos la aplicación de eureka localizada en la raíz y finalmente busBalancer, igualmente en
  la raíz.
- **BackWeb**: una vez realizados todos los pasos anteriores podríamos realizar peticiones a la siguiente ruta: _
  localhost:8080/api/v0_

## Manual de uso <a name="uso"></a>

### Front

Al lanzar la aplicación disponemos de un pequeño front en la ruta _localhost:8080_ donde podremos buscar autobuses para
una fecha en concreto y, posteriormente hacer una reserva y obtener los datos de esta al correo introducido.  

Para realizar correctamente la reserva será necesario registrarse previamente: [autorización](#auth)
En el caso del front no es necesaria autorización ya que está en fase de desarrllo y automáticamente se crea la reserva
al reservarla.  

Actualmente hay 3 viajes disponibles en la base de datos, estos caducarán el 25/06/2022 por lo que una vez pasada esta
fecha, deberían introducirse manualmente nuevos viajes.

### Postman

Podremos revisar todos los endpoints en la ruta _localhost:8080/swagger-uihtml_ de esta forma tenemos localizadas las
rutas a introducir en postaman para realizar las peticiones.

### Autorización <a name="auth"></a>

Las peticiones que requieren de autorización previa son:
```
 PUT /correos
 GET /correos
 POST /reserva
```
Para ello simplemente debemos registrarnos en el endpoint _/api/v0/token_ usando el método POST, pasaremos el usuario y
password deseado y guardaremos el token para usarlo en el campo authorize de los endpoints comentados anteriormente.

NOTA: *Comentar que todos los endpoints van precedidos por: _api/v0_*


      
