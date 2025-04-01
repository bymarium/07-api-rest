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

#### **1.7 services/**
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

#### **1.8 controllers/**
- Contiene las clases que manejan las solicitudes HTTP y exponen endpoints.
- Cada controlador usa `@RestController` y `@RequestMapping` para definir rutas de la API.

Ejemplo de controlador (`ClientController.java`):
```java
@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @PostMapping
    public Client createClient(@RequestBody Client client) {
        return clientService.createClient(client);
    }
}
```

#### **1.8 utils/**
- Contiene clases con métodos utilitarios reutilizables en varias partes del proyecto.
- Puede incluir validaciones, conversores de formato y otras funciones auxiliares.

### **2. src/main/resources/**
Contiene archivos de configuración y recursos estáticos:

- `application.properties`: Archivo donde se configuran los parámetros de conexión a la base de datos.

Ejemplo de configuración:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/database_name
spring.datasource.username=root
spring.datasource.password=password
```

### **3. src/test/java/**
- Contiene las pruebas unitarias y de integración del proyecto.

## **Cómo Ejecutar el Proyecto**

### **1. Clonar el repositorio**
```bash
git clone https://github.com/bymarium/07-api-rest.git
cd 07-api-rest
```

### **2. Configurar la Base de Datos**
- Modifica `application.properties` con los datos de tu base de datos MySQL.

### **3. Construir y Ejecutar el Proyecto**
- Usando Gradle:
```bash
./gradlew bootRun
```
- Usando Maven:
```bash
mvn spring-boot:run
```

### **4. Probar la API con Postman**
Puedes probar la API importando una colección en Postman y enviando solicitudes a los siguientes endpoints:

- **Obtener todos los clientes**: `GET http://localhost:8080/clients`
- **Crear un cliente**: `POST http://localhost:8080/clients`
```json
{
  "name": "John Doe",
  "email": "johndoe@example.com"
}
```

---

### **Conclusión**
Este proyecto sigue la arquitectura MVC en Spring Boot, facilitando la organización del código y la escalabilidad. Además, implementa patrones de diseño para mejorar la mantenibilidad y flexibilidad. Si necesitas más detalles o colaboración, siéntete libre de contribuir al repositorio. 🚀
