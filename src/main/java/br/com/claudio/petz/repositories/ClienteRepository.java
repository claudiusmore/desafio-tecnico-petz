package br.com.claudio.petz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.claudio.petz.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{	

}
