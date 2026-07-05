# Climalert

Sistema de monitoreo climático desarrollado en **Java 21** con **Spring Boot**.

La aplicación consulta periódicamente WeatherAPI, guarda las mediciones climáticas y genera alertas cuando se detectan condiciones críticas.

## Tecnologías

* Java 21
* Spring Boot
* Spring Data JPA
* H2 Database
* Spring Mail
* WeatherAPI
* Scheduler con `@Scheduled`

## Reglas de alerta

Se genera una alerta cuando:

* Temperatura mayor a 35°C
* Humedad mayor a 60%

## Destinatarios de alerta

Cuando se genera una alerta, el sistema envía un correo a:

* [admin@clima.com](mailto:admin@clima.com)
* [emergencias@clima.com](mailto:emergencias@clima.com)
* [meteorologia@clima.com](mailto:meteorologia@clima.com)

El correo incluye el detalle del clima registrado.

## Configuración

La API key de WeatherAPI debe configurarse como variable de entorno:

```cmd
set WEATHER_API_KEY=TU_API_KEY
```

La ubicación por defecto es CABA.

## Configuración de mail

El envío de correos puede activarse con:

```cmd
set CLIMALERT_MAIL_ENABLED=true
```

También se puede configurar el servidor SMTP mediante variables de entorno:

```cmd
set SPRING_MAIL_HOST=localhost
set SPRING_MAIL_PORT=1025
set SPRING_MAIL_USERNAME=
set SPRING_MAIL_PASSWORD=
```

## Ejecutar

```cmd
mvn spring-boot:run
```

## Tests

```cmd
mvn clean test
```

## Health check

```text
http://localhost:8080/actuator/health
```

## Notas

No subir claves privadas ni archivos generados como `target/` o `data/`.
