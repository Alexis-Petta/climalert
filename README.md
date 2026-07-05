# Climalert

Climalert es un servicio autonomo desarrollado en **Java 21** con **Spring Boot**. No tiene interfaz grafica: se ejecuta en segundo plano, consulta WeatherAPI periodicamente, guarda el historial climatico localmente y genera alertas por correo cuando detecta condiciones criticas.

## Requerimiento cubierto

- Integracion REST con **WeatherAPI** mediante `/current.json`.
- Ubicacion fija configurable, por defecto coordenadas de CABA (`-34.6037,-58.3816`).
- Consulta automatica cada **5 minutos**.
- Persistencia local de cada medicion climatica para historial.
- Analisis automatico cada **1 minuto** sobre la ultima medicion disponible.
- Generacion de alerta cuando:
  - temperatura `> 35°C`
  - humedad `> 60%`
- Envio de correo a:
  - `admin@clima.com`
  - `emergencias@clima.com`
  - `meteorologia@clima.com`

## Arquitectura

El proyecto esta dividido en capas simples:

```text
src/main/java/ar/edu/utn/dsi/climalert
├── client          # Cliente REST contra WeatherAPI
├── config          # Beans y propiedades de configuracion
├── domain          # Entidades JPA y enums del dominio
├── repository      # Repositorios Spring Data JPA
├── scheduler       # Jobs periodicos con @Scheduled
└── service         # Casos de uso: historial, evaluacion de alertas y notificaciones
```

## Requisitos

- Java 21
- Maven 3.9+
- API key de WeatherAPI
- Opcional: servidor SMTP para probar el envio real de emails

## Configuracion

Copiar `.env.example` a `.env` o definir las variables de entorno manualmente.

Variables principales:

| Variable | Descripcion | Valor por defecto |
|---|---|---|
| `WEATHER_API_KEY` | API key de WeatherAPI | vacio |
| `CLIMALERT_LOCATION` | Ubicacion a consultar. Por defecto usa coordenadas de CABA para evitar ambiguedades con WeatherAPI. | `-34.6037,-58.3816` |
| `CLIMALERT_WEATHER_POLL_MS` | Intervalo de consulta al proveedor | `300000` |
| `CLIMALERT_ALERT_CHECK_MS` | Intervalo de analisis de alertas | `60000` |
| `CLIMALERT_MAX_TEMP_C` | Temperatura critica | `35` |
| `CLIMALERT_MIN_HUMIDITY` | Humedad critica | `60` |
| `CLIMALERT_MAIL_ENABLED` | Habilita envio real de emails | `false` |

## Ejecucion local

Desde la raiz del proyecto:

```bash
mvn clean test
mvn spring-boot:run
```

En Windows PowerShell, ejemplo minimo:

```powershell
$env:WEATHER_API_KEY="TU_API_KEY"
$env:CLIMALERT_MAIL_ENABLED="false"
mvn spring-boot:run
```

Con envio de correo habilitado:

```powershell
$env:WEATHER_API_KEY="TU_API_KEY"
$env:CLIMALERT_MAIL_ENABLED="true"
$env:SPRING_MAIL_HOST="smtp.gmail.com"
$env:SPRING_MAIL_PORT="587"
$env:SPRING_MAIL_USERNAME="tu_mail@gmail.com"
$env:SPRING_MAIL_PASSWORD="tu_password_o_app_password"
mvn spring-boot:run
```

## Base local

Se usa H2 en modo archivo. La base queda en:

```text
./data/climalert-db
```

Esto permite conservar el historial entre ejecuciones locales.

## Health check

El proyecto incluye Actuator. Con la app levantada:

```text
GET http://localhost:8080/actuator/health
```

## Flujo principal

1. `WeatherPollingScheduler` se ejecuta cada 5 minutos.
2. `WeatherApiClient` consulta WeatherAPI.
3. `WeatherHistoryService` guarda la medicion en H2.
4. `AlertAnalysisScheduler` se ejecuta cada 1 minuto.
5. `WeatherAlertService` toma la ultima medicion y evalua condiciones criticas.
6. Si corresponde, guarda una alerta y delega el envio a `EmailAlertNotifier`.
7. Para no duplicar alertas, solo se genera una alerta por medicion climatica.

## Subir a GitHub

Crear un repositorio publico en GitHub y ejecutar:

```bash
git init
git add .
git commit -m "Implementacion inicial de Climalert"
git branch -M main
git remote add origin https://github.com/TU_USUARIO/climalert.git
git push -u origin main
```

Luego entregar el link publico del repositorio.
