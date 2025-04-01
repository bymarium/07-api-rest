# API REST - Documentación

## **Índice**
1. [Descripción del Proyecto](#descripción-del-proyecto)
2. [Estructura del Proyecto](#estructura-del-proyecto)
    - [1. src/main/java/com/example/restaurant/](#1-srcmainjavacomexamplerestaurant)
        - [1.1 config/](#11-config)
        - [1.2 constants/](#12-constants)
        - [1.3 models/](#13-models)
        - [1.4 dtos/](#14-dtos)
        - [1.5 repositories/](#15-repositories)
        - [1.6 services/](#16-services)
        - [1.7 controllers/](#17-controllers)
        - [1.8 utils/](#18-utils)
            - [1.8.1 converters/](#181-converters)
            - [1.8.2 prices/](#181-prices)
   - [2. src/main/resources/](#2-srcmainresources)
   - [3. src/test/java/](#3-srctestjava)
3. [Cómo Ejecutar el Proyecto](#cómo-ejecutar-el-proyecto)
   - [1. Clonar el Repositorio](#1-clonar-el-repositorio)
   - [2. Configurar la Base de Datos](#2-configurar-la-base-de-datos)
   - [3. Construir y Ejecutar el Proyecto](#3-construir-y-ejecutar-el-proyecto)
   - [4. Acceder a la Aplicación](#4-acceder-a-la-aplicación)
   - [5. Probar la API con Postman](#5-probar-la-api-con-postman)

## **Descripción del Proyecto**
Este proyecto es una API REST desarrollada con **Spring Boot**. Proporciona una estructura organizada para gestionar datos mediante un sistema de modelos, controladores, servicios y repositorios.

## **Estructura del Proyecto**

### **1. src/main/java/com/example/restaurant/**
Este es el paquete principal del proyecto, que contiene las siguientes carpetas clave:

#### **1.1 config/**
- Contiene clases de configuración para la aplicación.
- Aquí se pueden definir configuraciones de seguridad, CORS y otros aspectos globales del proyecto.
- **SwaggerConfig.java**: Configura **Swagger** para generar documentación automática de la API. Swagger proporciona una interfaz gráfica para visualizar y probar los endpoints de la API.
  - Puedes acceder a la documentación interactiva de la API a través de Swagger UI en `http://localhost:8080/swagger-ui/`.

  Para ver el código completo de la configuración de Swagger, consulta el archivo [SwaggerConfig.java en GitHub](https://github.com/bymarium/07-api-rest/blob/main/src/main/java/com/example/restaurant/config/SwaggerConfig.java).

#### **1.2 constants/**
- Almacena constantes globales utilizadas en toda la aplicación.
- Útil para evitar valores "hardcoded" en el código.
- **Type.java**: Este archivo define un **enumerador** (`enum`) que contiene tres tipos: `COMMON`, `FREQUENT`, y `POPULAR`. Cada uno tiene un nombre asociado que se puede obtener mediante el método `getName()`. Estos valores se utilizan para categorizar ciertos elementos dentro de la aplicación.

  Para ver el código completo del enumerador, consulta el archivo [Type.java en GitHub](https://github.com/bymarium/07-api-rest/blob/main/src/main/java/com/example/restaurant/constants/Type.java).

#### **1.3 models/**
- Contiene las clases que representan las entidades de la base de datos.
- Cada modelo usa anotaciones de JPA (`@Entity`, `@Id`, `@GeneratedValue`, etc.) para mapear la base de datos.
- Estos modelos son utilizados para crear, leer, actualizar y eliminar datos en la base de datos mediante el uso de **JPA** y **Hibernate**.

A continuación, se describen algunos de los modelos más importantes:

1. **Client.java**: Representa a un cliente en el sistema. Cada cliente tiene un nombre, apellido, correo electrónico, tipo de usuario y una lista de pedidos (`orders`).
   - [Ver código en GitHub](https://github.com/bymarium/07-api-rest/blob/main/src/main/java/com/example/restaurant/models/Client.java)

2. **Dish.java**: Representa un plato del menú. Incluye el nombre del plato, su descripción, precio y tipo de plato. Además, tiene una relación con el menú al que pertenece (`menu`) y con los detalles del pedido (`orderDetails`).
   - [Ver código en GitHub](https://github.com/bymarium/07-api-rest/blob/main/src/main/java/com/example/restaurant/models/Dish.java)

3. **Menu.java**: Representa un menú que agrupa varios platos. Cada menú tiene un nombre, una descripción y una lista de platos asociados (`dishes`).
   - [Ver código en GitHub](https://github.com/bymarium/07-api-rest/blob/main/src/main/java/com/example/restaurant/models/Menu.java)

4. **Order.java**: Representa un pedido realizado por un cliente. Contiene la fecha del pedido, el precio total y una lista de detalles del pedido (`orderDetails`).
   - [Ver código en GitHub](https://github.com/bymarium/07-api-rest/blob/main/src/main/java/com/example/restaurant/models/Order.java)

5. **OrderDetail.java**: Representa los detalles de un pedido, es decir, un plato específico y la cantidad que fue ordenada. Contiene el precio unitario y el subtotal del plato.
   - [Ver código en GitHub](https://github.com/bymarium/07-api-rest/blob/main/src/main/java/com/example/restaurant/models/OrderDetail.java)

**Ejemplo de un modelo (`Client.java`)**:
```java
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String userType = Type.COMMON.getName();
    private Float adjust = 1f;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Order> orders;

    public Client(Long id, String name, String lastName, String email) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }

    public Client() {
    }
}
```
#### **1.4 dtos/**
- Contiene clases para la transferencia de datos (**DTOs - Data Transfer Objects**).
- Los DTOs se utilizan para estructurar la información enviada y recibida en los endpoints de la API REST.
- Estos objetos permiten separar la lógica de negocio de la representación de los datos en las respuestas y peticiones, y se usan para mapear los datos de las solicitudes HTTP a objetos Java.

##### Los siguientes DTOs están disponibles:

1. **ClientDTO.java**: Representa un cliente con los campos `name`, `lastName` y `email`. Es utilizado para realizar las peticiones HTTP al crear o actualizar un cliente.
   - [Ver código en GitHub](https://github.com/bymarium/07-api-rest/blob/main/src/main/java/com/example/restaurant/dtos/ClientDTO.java)

2. **DishDTO.java**: Representa un plato con los campos `name`, `description`, `price` y `menuId`. Es utilizado para realizar las peticiones HTTP al crear o actualizar un plato en el menú.
   - [Ver código en GitHub](https://github.com/bymarium/07-api-rest/blob/main/src/main/java/com/example/restaurant/dtos/DishDTO.java)

3. **MenuDTO.java**: Representa un menú con los campos `name` y `description`. Es utilizado para realizar las peticiones HTTP al crear o actualizar un menú.
   - [Ver código en GitHub](https://github.com/bymarium/07-api-rest/blob/main/src/main/java/com/example/restaurant/dtos/MenuDTO.java)

4. **OrderDTO.java**: Representa un pedido realizado por un cliente, con el campo `clientId` (que identifica al cliente) y `orderDetails` (una lista de detalles del pedido). Es utilizado para realizar peticiones HTTP relacionadas con la creación de un pedido.
   - [Ver código en GitHub](https://github.com/bymarium/07-api-rest/blob/main/src/main/java/com/example/restaurant/dtos/OrderDTO.java)

5. **OrderDetailDTO.java**: Representa un detalle de un pedido, con los campos `dishId` (que identifica al plato) y `quantity` (la cantidad del plato). Es utilizado dentro del DTO de `OrderDTO` para especificar los detalles de los platos en un pedido.
   - [Ver código en GitHub](https://github.com/bymarium/07-api-rest/blob/main/src/main/java/com/example/restaurant/dtos/OrderDetailDTO.java)

##### **MessageDTO.java**: Este DTO se utiliza para estructurar las respuestas de la API. Es utilizado para enviar mensajes de respuesta junto con los detalles de los resultados de una operación.
  - Contiene un mensaje (`message`) que describe el resultado de la operación y un campo opcional (`details`) que puede contener información adicional sobre el resultado.
  - Se utiliza para devolver respuestas estandarizadas en las peticiones HTTP, como éxito, error, o mensajes de validación.
  
  **Ejemplo de `MessageDTO`**:
  ```java
  @Getter
  @Setter
  @NoArgsConstructor
  public class MessageDTO {
     private String message;
     private Object details;
  
     public MessageDTO(String message, Object details) {
         this.message = message;
         this.details = details;
     }
  
     public MessageDTO(String message) {
         this.message = message;
     }
  }
  ```

#### **1.5 repositories/**
- Contiene las interfaces que permiten interactuar con la base de datos usando Spring Data JPA.
- Se extiende de `JpaRepository<Tipo, ID>`.

##### Archivos en la carpeta `repositories`:

1. **IClientRepository.java**:
   - Proporciona los métodos necesarios para interactuar con la entidad `Client` (Cliente).
   - [Ver código en GitHub](https://github.com/bymarium/07-api-rest/blob/main/src/main/java/com/example/restaurant/repositories/IClientRepository.java)
     
2. **IDishRepository.java**:
   - Proporciona los métodos necesarios para interactuar con la entidad `Dish` (Plato).
   - [Ver código en GitHub](https://github.com/bymarium/07-api-rest/blob/main/src/main/java/com/example/restaurant/repositories/IDishRepository.java)
     
3. **IMenuRepository.java**:
   - Proporciona los métodos necesarios para interactuar con la entidad `Menu` (Menú).
   - [Ver código en GitHub](https://github.com/bymarium/07-api-rest/blob/main/src/main/java/com/example/restaurant/repositories/IMenuRepository.java)
     
4. **IOrderDetailRepository.java**:
   - Proporciona los métodos necesarios para interactuar con la entidad `OrderDetail` (Detalle de Pedido).
   - Incluye una consulta personalizada `findByDishId(Long dishId)` para obtener los detalles de los pedidos de un plato específico.
   - [Ver código en GitHub](https://github.com/bymarium/07-api-rest/blob/main/src/main/java/com/example/restaurant/repositories/IOrderDetailRepository.java)
     
   **Ejemplo de `IOrderDetailRepository`**:
    ```java
    public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Long> {
        List<OrderDetail> findByDishId(Long dishId);
    }
    ```

5. **IOrderRepository.java**:
   - Proporciona los métodos necesarios para interactuar con la entidad `Order` (Pedido).
   - Incluye una consulta personalizada `countByClient_Id(Long clientId)` para contar la cantidad de pedidos de un cliente específico.
   - [Ver código en GitHub](https://github.com/bymarium/07-api-rest/blob/main/src/main/java/com/example/restaurant/repositories/IOrderRepository.java)

   **Ejemplo de `IOrderRepository`**:
    ```java
    public interface IOrderRepository extends JpaRepository<Order, Long> {
        Long countByClient_Id(Long clientId);
    }
    ```

#### **1.6 services/**
- Contiene la lógica de negocio de la aplicación.
- Se organiza en subcarpetas según la entidad a la que pertenece cada servicio.
- Implementa los principios **SOLID** y varios **patrones de diseño** como:
  - **Command Pattern**: Se usa para encapsular solicitudes como objetos, permitiendo la parametrización de clientes con diferentes solicitudes.
  - **Observer Pattern**: Se utiliza para reaccionar a cambios en los datos sin modificar directamente las clases afectadas.
  - **Chain of Responsibility**: Implementado para manejar flujos de validación y procesamiento de datos mediante una cadena de responsabilidades.

Ejemplo de servicio (`ClientService.java`):
```java
@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }
}
```

#### **1.7 Controllers**
- Contiene las clases que manejan las solicitudes HTTP y exponen los endpoints de la API.
- Cada controlador usa `@RestController` y `@RequestMapping` para definir las rutas de la API.
- Estos controladores interactúan con los servicios correspondientes y devuelven respuestas adecuadas a las solicitudes HTTP.

**Ejemplo de controlador (ClientController.java):**

Enlace al archivo: [ClientController.java](https://github.com/bymarium/07-api-rest/blob/main/src/main/java/com/example/restaurant/controllers/ClientController.java)

Este controlador gestiona las operaciones CRUD (crear, obtener, actualizar y eliminar) para los clientes. Los métodos expuestos son:

- **POST** `/api/clients`: Crea un nuevo cliente. Devuelve un mensaje de éxito junto con la información del cliente creado.
- **GET** `/api/clients/{clientId}`: Obtiene la información de un cliente por su ID.
- **GET** `/api/clients`: Obtiene la lista de todos los clientes.
- **PUT** `/api/clients/{clientId}`: Actualiza la información de un cliente específico.
- **DELETE** `/api/clients/{clientId}`: Elimina un cliente por su ID.

**Ejemplo del controlador GlobalExceptionHandler.java:**

Enlace al archivo: [GlobalExceptionHandler.java](https://github.com/bymarium/07-api-rest/blob/main/src/main/java/com/example/restaurant/controllers/GlobalExceptionHandler.java)

El controlador `GlobalExceptionHandler` maneja excepciones globales en la aplicación. Usa `@RestControllerAdvice` para definir un controlador de excepciones globales. En este caso, se maneja específicamente la excepción `RuntimeException`. Cuando se lanza una `RuntimeException`, el manejador devuelve un objeto `MessageDTO` con un mensaje de error y un código HTTP 404 (Not Found).

- **Manejo de excepciones:** Si ocurre una `RuntimeException`, el método `handleRuntimeException` captura la excepción y devuelve un mensaje con el texto de la excepción.
  
  ```java
  @RestControllerAdvice
  public class GlobalExceptionHandler {
      @ExceptionHandler(RuntimeException.class)
      @ResponseStatus(HttpStatus.NOT_FOUND)
      public MessageDTO handleRuntimeException(RuntimeException ex) {
          return new MessageDTO(ex.getMessage());
      }
  }
  
Este controlador garantiza que las excepciones no controladas sean manejadas de manera centralizada, proporcionando respuestas consistentes para los errores en toda la API.

**Detalles de otros controladores: **
- [DishController.java](https://github.com/bymarium/07-api-rest/blob/main/src/main/java/com/example/restaurant/controllers/DishController.java): Maneja las operaciones CRUD para los platos del restaurante.
- [MenuController.java](https://github.com/bymarium/07-api-rest/blob/main/src/main/java/com/example/restaurant/controllers/MenuController.java): Gestiona las operaciones CRUD para los menús del restaurante.
- [OrderController.java](https://github.com/bymarium/07-api-rest/blob/main/src/main/java/com/example/restaurant/controllers/OrderController.java): Gestiona las operaciones CRUD para los pedidos del restaurante.

Todos estos controladores siguen un patrón similar, asegurando que cada recurso (cliente, plato, menú, pedido) tenga sus propias rutas y métodos para interactuar con ellos

#### **1.8 utils/**
Esta carpeta contiene clases con métodos utilitarios reutilizables en varias partes del proyecto. Incluye:

- **`converters/`**: Contiene clases encargadas de convertir objetos DTO (Data Transfer Object) en entidades del modelo de datos.
- **`prices/`**: Implementa el patrón de diseño Chain of Responsibility para aplicar descuentos según el tipo de cliente.

---

##### **1.8.1 converters/**
Las clases en esta carpeta permiten convertir objetos DTO a entidades del modelo de datos. Esto es útil para mantener la separación de responsabilidades y facilitar la manipulación de datos en el sistema.

**Enlaces a los conversores:**
- [ClientConverter.java](https://github.com/bymarium/07-api-rest/blob/main/src/main/java/com/example/restaurant/utils/Converters/ClientConverter.java)
- [DishConverter.java](https://github.com/bymarium/07-api-rest/blob/main/src/main/java/com/example/restaurant/utils/Converters/DishConverter.java)
- [MenuConverter.java](https://github.com/bymarium/07-api-rest/blob/main/src/main/java/com/example/restaurant/utils/Converters/MenuConverter.java)
- [OrderConverter.java](https://github.com/bymarium/07-api-rest/blob/main/src/main/java/com/example/restaurant/utils/Converters/OrderConverter.java)
- [OrderDetailConverter.java](https://github.com/bymarium/07-api-rest/blob/main/src/main/java/com/example/restaurant/utils/Converters/OrderDetailConverter.java)

##### **1.8.2 prices/**
Esta carpeta implementa el **patrón Chain of Responsibility** para aplicar descuentos en función del tipo de cliente. Se define una jerarquía de `Handlers` que procesan las solicitudes de descuento de manera encadenada.

**Ejemplo de Implementación**
**Clase Base: `Handler.java`**
```java
public abstract class Handler {
    protected Handler nextHandler;

    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract void handlerRequest(Order order);
}
```
**Explicación:**
- `Handler` es una clase abstracta que define un manejador con una referencia a otro `Handler` (`nextHandler`).
- La implementación concreta de `handlerRequest` se define en las subclases.

**Manejador para Clientes Comunes: `CommonClient.java`**
```java
public class CommonClient extends Handler {
    @Override
    public void handlerRequest(Order order) {
        if (nextHandler != null && !order.getClient().getUserType().equals(Type.COMMON.getName())) {
            nextHandler.handlerRequest(order);
        }
    }
}
```
**Explicación:**
- Si el cliente no es de tipo `COMMON`, la solicitud pasa al siguiente manejador.

**Manejador para Clientes Frecuentes: `FrequentClient.java`**
```java
public class FrequentClient extends Handler {
    @Override
    public void handlerRequest(Order order) {
        if (order.getClient().getUserType().equals(Type.FREQUENT.getName())) {
            order.setTotalPrice(order.getTotalPrice() * order.getClient().getAdjust());
        }
    }
}
```
**Explicación:**
- Si el cliente es de tipo `FREQUENT`, se aplica un ajuste al precio total del pedido.

### **2. src/main/resources/**
Contiene archivos de configuración y recursos estáticos:

- `application.properties`: Archivo donde se configuran los parámetros de conexión a la base de datos y la configuración de JPA/Hibernate.

Ejemplo de configuración:
```properties
spring.application.name=restaurant
# JPA/Hibernate configuration
spring.jpa.hibernate.ddl-auto=update
# MySQL connection configuration using environment variables
spring.datasource.url=jdbc:mysql://${HOST}:${PORT}/${NAME}?createDatabaseIfNotExist=true
spring.datasource.username=${USER}
spring.datasource.password=${PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

**Recuerda configurar tus variables de entorno HOST, PORT, NAME, USER, PASSWORD con tus credenciales**

### **3. src/test/java/**
- Contiene las pruebas unitarias y de integración del proyecto.

## **Cómo Ejecutar el Proyecto**

### **1. Clonar el Repositorio**
Clona el repositorio y accede a la carpeta del proyecto:
```bash
git clone https://github.com/bymarium/07-api-rest.git
cd 07-api-rest
```

### **2. Configurar la Base de Datos**
- Asegúrate de tener MySQL instalado y en ejecución.
- Modifica el archivo `application.properties` ubicado en `src/main/resources/` con las credenciales y datos de tu base de datos.

Ejemplo:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/nombre_base_datos
spring.datasource.username=usuario
spring.datasource.password=contraseña
```

### **3. Construir y Ejecutar el Proyecto**
Este proyecto utiliza **Gradle** por defecto, pero también puedes ejecutarlo con **Maven** si lo prefieres.

#### **Usando Gradle** (recomendado)
```bash
./gradlew bootRun
```
Si usas Windows:
```bash
gradlew.bat bootRun
```

#### **Usando Maven**
```bash
mvn spring-boot:run
```

### **4. Acceder a la Aplicación**
Una vez que la aplicación esté en ejecución, puedes acceder a la API en:
```
http://localhost:8080
```
La aplicación usa Swagger para la documentación de la API, puedes verificar los endpoints en:
```
http://localhost:8080/swagger-ui.html
```

### **5. Probar la API con Postman**
Puedes probar la API importando una colección en Postman y enviando solicitudes a los siguientes endpoints:

- **Obtener todos los clientes**: `GET http://localhost:8080/api/clients`
- **Obtener un cliente**: `GET http://localhost:8080/api/clients/{id}`
- **Crear un cliente**: `POST http://localhost:8080/api/clients`
```json
{
  "name": "John",
  "lastName": "Doe",
  "email": "johndoe@example.com"
}
```
- **Actualizar un cliente**: `PUT http://localhost:8080/api/clients/{id}`
```json
{
  "name": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com"
}
```
- **Eliminar un cliente**: `PUT http://localhost:8080/api/clients{id}`



### **Conclusión**
Este proyecto sigue la arquitectura MVC en Spring Boot, facilitando la organización del código y la escalabilidad. Además, implementa patrones de diseño para mejorar la mantenibilidad y flexibilidad. Si necesitas más detalles o colaboración, siéntete libre de contribuir al repositorio. 
