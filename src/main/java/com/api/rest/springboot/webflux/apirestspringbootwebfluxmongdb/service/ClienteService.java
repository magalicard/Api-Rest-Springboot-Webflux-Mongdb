package com.api.rest.springboot.webflux.apirestspringbootwebfluxmongdb.service;

import documentos.Cliente;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClienteService {
    public Flux<Cliente> findAll(); //es mi flujo de elementos observable
    public Mono<Cliente> findById(String id); //retorna un solo elemento ( mono) este observable
    public Mono<Cliente> save(Cliente cliente);
    public Mono<Void> delete(Cliente cliente);
}
