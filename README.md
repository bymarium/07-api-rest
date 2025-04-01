# API REST - Documentación

## Descripción del Proyecto
Este proyecto es una API REST desarrollada con **Spring Boot**. Proporciona una estructura organizada para manejar datos mediante un sistema de modelos, controladores, servicios y repositorios.

## Estructura del Proyecto

### **1. src/main/java/com/example/demo/**
Este es el paquete principal del proyecto, que contiene las siguientes carpetas clave:

#### **1.1 models/**
- Contiene las clases que representan las entidades de la base de datos.
- Cada modelo usa anotaciones de JPA (`@Entity`, `@Id`, `@GeneratedValue`, etc.) para mapear la base de datos.

Ejemplo de modelo (`Client.java`):
```java
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    // Getters y setters
}
```

#### **1.2 controllers/**
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

#### **1.3 services/**
- Contiene la lógica de negocio de la aplicación.
- Se encarga de procesar datos y comunicarse con los repositorios.

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

#### **1.4 repositories/**
- Contiene las interfaces que permiten interactuar con la base de datos usando Spring Data JPA.
- Se extiende de `JpaRepository<Tipo, ID>`.

Ejemplo de repositorio (`ClientRepository.java`):
```java
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
```

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
Este proyecto sigue la arquitectura MVC en Spring Boot, facilitando la organización del código y la escalabilidad. Si necesitas más detalles o colaboración, siéntete libre de contribuir al repositorio. 🚀
