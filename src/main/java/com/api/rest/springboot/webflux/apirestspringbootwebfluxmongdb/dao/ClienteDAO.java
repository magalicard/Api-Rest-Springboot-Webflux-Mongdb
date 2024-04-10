package com.api.rest.springboot.webflux.apirestspringbootwebfluxmongdb.dao;

import documentos.Cliente;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ClienteDAO extends ReactiveMongoRepository<Cliente, String> {
}
