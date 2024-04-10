package com.api.rest.springboot.webflux.apirestspringbootwebfluxmongdb.Controller;

import com.api.rest.springboot.webflux.apirestspringbootwebfluxmongdb.service.ClienteService;
import documentos.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.File;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @Value("${config.uploads.path}") //Aca se guardan las imágenes
    private String path;

    @PostMapping("/registerWithPic")
    public Mono<ResponseEntity<Cliente>> registrarClienteConFoto(Cliente cliente, @RequestPart FilePart file){
        cliente.setFoto(UUID.randomUUID().toString() + "-" + file.filename()
                .replace(" ", "")
                .replace(":", "")
                .replace("//", ""));

        //guardamos
        return file.transferTo(new File(path + cliente.getFoto()))
                .then(clienteService.save(cliente))
                .map(c -> ResponseEntity.created(URI.create("/api/clientes".concat(c.getId())))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(c));


    }
}
