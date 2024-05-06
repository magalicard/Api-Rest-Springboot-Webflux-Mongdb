
API Reactiva con Java y Spring WebFlux

Este repositorio contiene una API Reactiva implementada en Java utilizando Spring WebFlux, Hibernate Reactive y MongoDB. La API permite:

Guardar clientes con sus fotos en MongoDB.
Buscar clientes por identificador.
Listar todos los clientes.
Editar la información de un cliente.
Eliminar un cliente.

Tecnologías Utilizadas

Java 1.8
Spring WebFlux 5+ (asume Spring Framework 5+)
Hibernate Reactive 5+
MongoDB Java Driver
Spring Data MongoDB (opcional, basado en el código proporcionado)
Estructura del Proyecto

El proyecto se estructura de la siguiente manera:

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
Funcionamiento

La API expone un conjunto de endpoints REST para gestionar clientes:

Registrar un cliente con foto (POST /api/clientes/registerWithPic)
Recibe un objeto Cliente y un archivo multipart con la foto del cliente.
Genera un nombre único para la foto.
Almacena la foto en la carpeta configurada (config.uploads.path).
Guarda el cliente con la referencia a la foto en MongoDB.
Devuelve la información del cliente creado.
Subir foto de un cliente existente (PUT /api/clientes/{id}/upload)
Recibe el identificador del cliente y un archivo multipart con la nueva foto.
Busca el cliente por su identificador.
Genera un nombre único para la nueva foto.
Almacena la nueva foto en la carpeta configurada.
Actualiza la referencia a la foto en el objeto Cliente.
Guarda el cliente actualizado en MongoDB.
Devuelve la información del cliente actualizado (con la nueva referencia a la foto).
Listar todos los clientes (GET /api/clientes)
Obtiene todos los clientes de la base de datos.
Devuelve un flujo con la información de todos los clientes.
Ver detalles de un cliente (GET /api/clientes/{id})
Recibe el identificador del cliente.
Busca el cliente por su identificador.
Devuelve la información del cliente encontrado.
Guardar un cliente (POST /api/clientes)
Recibe un objeto Cliente.
Guarda el cliente en MongoDB.
Devuelve la información del cliente creado.
Editar un cliente (PUT /api/clientes/{id})
Recibe el identificador y los nuevos datos del cliente.
Busca el cliente por su identificador.
Actualiza los datos del cliente.
Guarda el cliente actualizado en MongoDB.
Devuelve la información del cliente actualizado.
Eliminar un cliente (DELETE /api/clientes/{id})
Recibe el identificador del cliente.
Busca el cliente por su identificador.
Elimina el cliente de la base de datos.
Devuelve un código de estado indicando el éxito o fracaso de la operación.
