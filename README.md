## API REST

Es una interfaz de programación de aplicaciones (API) que sigue los principios de la arquitectura REST (Representational State Transfer).

Es un conjunto de reglas que permite a sistemas interactuar entre sí utilizando métodos estándar del protocolo HTTP, como GET, POST, PUT, DELETE, etc.
El término "API REST" se usa para referirse a una API que implementa parcialmente o completamente las restricciones definidas por REST.

## RESTful

Es un adjetivo que describe una API que cumple completamente con los principios de REST.

Una API es RESTful si sigue estrictamente los siguientes principios:
- Arquitectura cliente-servidor: Separación entre cliente (interfaz) y servidor (datos y lógica).

- Stateless: Cada solicitud del cliente al servidor debe contener toda la información necesaria para entenderla.

- Caché: Las respuestas deben ser cacheables cuando sea posible para mejorar el rendimiento.

- Interfaz uniforme: Uso consistente de métodos HTTP, URIs, y representaciones de recursos.

- Sistema en capas: Posibilidad de usar capas intermedias entre cliente y servidor.

## A que nos referimos con Stateless

En el contexto de REST, el término stateless (sin estado) significa que cada solicitud del cliente al servidor debe ser independiente y contener toda la información necesaria para que el servidor pueda procesarla, sin depender del estado de solicitudes anteriores.

## Que haremos en este repositorio...

Vamos a estructurar un API Restful para la heladería. Usaremos Java 17 con Gradle y organizaremos el proyecto de manera que sea fácil de extender. Nos enfocaremos en el inventario, que incluirá funcionalidades para gestionar productos, como agregar, actualizar, eliminar y listar.

Para empezar, estructuraremos el proyecto con estas consideraciones:

- Controladores: Gestionarán las rutas del API.
- Servicios: Contendrán la lógica del negocio.
- Modelos: Representarán las entidades de dominio (como "Producto").
- Repositorios en memoria: Simularán la interacción con la base de datos, usando colecciones como Map, Set y LinkedList.

Patrones de diseño:

- Singleton para manejar el repositorio.
- DTO para separar las entidades de dominio de los datos de respuesta o petición.
