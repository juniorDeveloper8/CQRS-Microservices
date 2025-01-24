package com.rober.repository;

import com.rober.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, String> {

    Client findByIdClient(String idClient);
}
