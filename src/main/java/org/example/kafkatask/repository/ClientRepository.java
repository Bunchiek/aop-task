package org.example.kafkatask.repository;

import org.example.kafkatask.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

}