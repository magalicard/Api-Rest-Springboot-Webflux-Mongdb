package com.api.rest.springboot.webflux.apirestspringbootwebfluxmongdb.Controller;

import com.api.rest.springboot.webflux.apirestspringbootwebfluxmongdb.service.ClienteService;
import documentos.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.io.File;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @Value("${config.uploads.path}") //Aca se guardan las imágenes
    private String path;

    //registrar un cliente con una foto
    @PostMapping("/registerWithPic")
    public Mono<ResponseEntity<Cliente>> registrarClienteConFoto(Cliente cliente, @RequestPart FilePart file){
        cliente.setFoto(UUID.randomUUID().toString() + "-" + file.filename()
                .replace(" ", "")
                .replace(":", "")
                .replace("//", ""));

        //guardamos la foto en esa ruta y en la bd
        return file.transferTo(new File(path + cliente.getFoto()))
                .then(clienteService.save(cliente))
                .map(c -> ResponseEntity.created(URI.create("/api/clientes/".concat(c.getId())))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(c));


    }

    //
    @PostMapping("/upload/{id}")
    public Mono<ResponseEntity<Cliente>> subirFoto(@PathVariable String id, @RequestPart FilePart file ){
        return clienteService.findById(id).flatMap(c -> {
            c.setFoto(UUID.randomUUID().toString() + "-" + file.filename()
                    .replace(" ", "")
                    .replace(":", "")
                    .replace("//", ""));

            //retornamos transfiriendo la foto y a guardamos
            return  file.transferTo(new File(path + c.getFoto())).then(clienteService.save(c));

        }).map(c -> ResponseEntity.ok(c)) //map transforma, le pasamos el cliente y devolvemos el estado
                .defaultIfEmpty(ResponseEntity.notFound().build()); //si es vacio decimos que no se encontró.

    }

    @GetMapping
    //leva mono porque devuelve un response entity, pero devuelve un flujo de clientes
    public Mono<ResponseEntity<Flux<Cliente>>> listarClientes(){
        return Mono.just( //just es para darle valores al flujo
            //eventos:
                ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(clienteService.findAll())
        );
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Cliente>> verDetallesDelCliente(@PathVariable String id){
        return clienteService.findById(id).map(c -> ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build()); //si no se encontró

    }

    //Guardamos un cliente
    @PostMapping
//le pasamos un mono que retorna un mapa
    public Mono<ResponseEntity<Map<String, Object>>> guardarCliente(@Valid @RequestBody Mono<Cliente> monoCliente){
        //creamos el map
        Map<String, Object> respuesta = new HashMap<>();

        //primero le pasamos un mono cliente (con lso datos que le voy adar)
        return monoCliente.flatMap(cliente -> {
            return clienteService.save(cliente).map(c -> { //al mapa le añadimos lo siguiente:
                respuesta.put("cliente", c); //add cliente
                respuesta.put("mensaje", "Cliente guardado con éxito");
                respuesta.put("timestamp", new Date());
                return ResponseEntity //retorno otra salida, la respuesta
                        .created(URI.create("/api/clientes/".concat(c.getId()))) //indicando la URI
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(respuesta);
            });
        //en caso de error ....
        }).onErrorResume(t -> {
            return Mono.just(t).cast(WebExchangeBindException.class)
                    .flatMap(e -> Mono.just(e.getFieldErrors())) //aca los guardamos
                    .flatMapMany(Flux::fromIterable)
                    .map(fieldError -> "El campo: " + fieldError.getField() + " " + fieldError.getDefaultMessage())
                    .collectList()
                    .flatMap(list -> {
                        respuesta.put("errors", list);
                        respuesta.put("timestamp", new Date());
                        respuesta.put("status", HttpStatus.BAD_REQUEST.value());

                        return Mono.just(ResponseEntity.badRequest().body(respuesta));
                    });
        });
    }

    //editamos cliente
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Cliente>> editarCliente(@RequestBody Cliente cliente, @PathVariable String id ){
        return clienteService.findById(id).flatMap(c -> {
            c.setNombre(cliente.getNombre());
            c.setApellido(cliente.getApellido());
            return clienteService.save(c);
        }).map(c -> ResponseEntity.created(URI.create("/api/clientes/".concat(c.getId())))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(c))
        .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    //Borrar
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> eliminarCliente(@PathVariable String id){
        return clienteService.findById(id).flatMap(c -> {
            return clienteService.delete(c).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
        }).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }
}
