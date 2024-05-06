<h1 style="color: #333; font-weight: bold;">API Reactiva con Java y Spring WebFlux</h1>

Este repositorio contiene una API Reactiva implementada en Java utilizando Spring WebFlux, Hibernate Reactive y MongoDB. 

**La API permite:**

- Guardar clientes con sus fotos en MongoDB.
- Buscar clientes por identificador.
- Listar todos los clientes.
- Editar la información de un cliente.
- Eliminar un cliente.

**Tecnologías Utilizadas**

- Java 1.8
- Spring WebFlux 5+ (asume Spring Framework 5+)
- Hibernate Reactive 5+
- MongoDB Java Driver
- Spring Data MongoDB (opcional, basado en el código proporcionado)


**El proyecto se estructura de la siguiente manera:**

src/main/java
com.api.rest.springboot.webflux.apirestspringbootwebfluxmongdb
Controller (Contiene los controladores REST)
ClienteController.java
dao (Se reemplaza por repository)
(Elimina ClienteDAO.java)
service (Contiene los servicios de negocio)
ClienteService.java
ClienteServiceImp.java
documentos (Contiene las entidades del dominio)
Cliente.java
resources (Contiene la configuración de la aplicación)
application.properties
test/java/com.api.rest.springboot.webflux.apirestspringbootwebfluxmongdb
ApiRestSpringbootWebfluxMongDbApplicationTests.java ( احتمالا تست های واحد)
uploads (carpeta para almacenar las imágenes)
SpringWebFluxUploads*.jpg (archivos de ejemplo)

<h3>Funcionamiento</h3>

La API ofrece endpoints REST para gestionar clientes

**Registro de clientes:**

- Se recibe información del cliente y una foto.
- Se genera un nombre único para la foto y se almacena en la carpeta configurada.
- Se guarda el cliente en MongoDB con la referencia a la foto.
- Se devuelve la información del cliente creado.

**Subida de fotos:**

- Se recibe el ID del cliente y una nueva foto.
- Se busca el cliente, se genera un nombre único para la nueva foto y se almacena.
- Se actualiza la referencia a la foto en el cliente y se guarda en MongoDB.
- Se devuelve la información del cliente actualizado.
  
**Consultas:**

- Listar todos los clientes: Obtiene y devuelve todos los clientes de la base de datos.
- Ver detalles de un cliente: Busca y devuelve la información del cliente por ID.

**Operaciones CRUD:**

- Crear cliente: Guarda un nuevo cliente en MongoDB y devuelve su información.
- Editar cliente: Actualiza los datos de un cliente existente y devuelve la información actualizada.
- Eliminar cliente: Elimina un cliente de MongoDB y devuelve un código de estado.
