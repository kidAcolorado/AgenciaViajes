# AgenciaViajes

# Proyecto de Gestión de Vuelos

Este proyecto es una aplicación web de gestión de vuelos que permite a los usuarios buscar, crear, actualizar y eliminar información de vuelos utilizando un servicio web.

## Descripción General

La aplicación se basa en un conjunto de controladores que gestionan las solicitudes relacionadas con las operaciones de vuelo. Cada controlador se encarga de una parte específica de la funcionalidad de la aplicación.

### Controladores Principales

#### `PasajeroViewController`

Este controlador se encarga de las vistas relacionadas con los pasajeros. Proporciona métodos para mostrar formularios, obtener y mostrar información sobre pasajeros, crear nuevos pasajeros, actualizar información de pasajeros y eliminar pasajeros utilizando el servicio web de pasajeros.

#### `ReservaViewController`

Gestiona las vistas relacionadas con las operaciones de reserva. Permite a los usuarios buscar vuelos, ver detalles de vuelos y realizar reservas.

#### `VueloViewController`

Controla las vistas relacionadas con las operaciones de vuelo. Proporciona métodos para mostrar formularios, obtener y mostrar información sobre vuelos, crear nuevos vuelos, actualizar información de vuelos y eliminar vuelos utilizando el servicio web de vuelos.

### Controladores Rest

Además de los controladores mencionados anteriormente, la aplicación también incluye controladores REST para la comunicación con el servicio web. Estos controladores manejan solicitudes RESTful y devuelven datos en formato JSON.

- :octocat: **`PasajeroRestController`**: Este controlador REST gestiona las solicitudes relacionadas con los pasajeros a través de la API REST. Permite obtener información sobre pasajeros y crear nuevos pasajeros.

- :octocat: **`ReservaRestController`**: Maneja las solicitudes REST relacionadas con las reservas de vuelos. Proporciona endpoints para crear y consultar reservas.

- :octocat: **`VueloRestController`**: Controla las operaciones REST relacionadas con los vuelos, incluyendo la búsqueda de vuelos y la gestión de la información de vuelos.

### Servicios

Los servicios son componentes clave de la aplicación que se encargan de la lógica de negocio y la comunicación con el servicio web externo. Cada controlador utiliza servicios para realizar operaciones relacionadas con los pasajeros, las reservas y los vuelos.

- :octocat: **`PasajeroService`**: Este servicio se encarga de la lógica de negocio relacionada con los pasajeros. Proporciona métodos para interactuar con el servicio web de pasajeros, como obtener información de pasajeros y crear nuevos registros de pasajeros.

- :octocat: **`ReservaService`**: Gestiona la lógica de negocio de las reservas de vuelos. Permite crear nuevas reservas y obtener información sobre reservas existentes.

- :octocat: **`VueloService`**: Controla la lógica de negocio relacionada con los vuelos. Proporciona métodos para buscar vuelos, obtener detalles de vuelos y administrar la información de vuelos.

## Configuración del Proyecto

Antes de ejecutar la aplicación, asegúrate de configurar correctamente los siguientes componentes:

- **Base de Datos**: Esta aplicación puede requerir una base de datos para almacenar información, dependiendo de la implementación.

- **WebClient**: Asegúrate de configurar el `WebClient` correctamente en cada controlador para comunicarte con el servicio web correspondiente.

## Requisitos

Asegúrate de tener instalados los siguientes componentes:

- Java SDK
- Spring Boot
- Spring Framework
- Dependencias adicionales según tu implementación específica.

## Cómo Ejecutar

1. Clona este repositorio en tu máquina local.

2. Configura las propiedades de la aplicación según tu entorno (por ejemplo, la URL del servicio web).

3. Ejecuta la aplicación utilizando tu IDE preferido o la línea de comandos:


4. Abre un navegador web y accede a la aplicación en la URL correspondiente (por ejemplo, `http://localhost:8080`).

## Contribuciones

Si deseas contribuir a este proyecto, por favor sigue estas pautas:

1. Haz un fork del repositorio.

2. Crea una rama para tu contribución: `git checkout -b feature/nueva-caracteristica`.

3. Realiza tus cambios y asegúrate de seguir las convenciones de codificación.

4. Haz commits con mensajes descriptivos: `git commit -m "Agrega nueva característica"`.

5. Envía un pull request a la rama principal del repositorio original.

## Licencia

Este proyecto está bajo la Licencia MIT. Consulta el archivo [LICENSE](LICENSE) para obtener más detalles.

---

**Autor:** Víctor Colorado "Kid A"

**Versión:** 1.0

**Desde:** 19 de septiembre de 2023
