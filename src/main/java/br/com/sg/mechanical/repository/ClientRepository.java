package br.com.sg.mechanical.repository;

import br.com.sg.mechanical.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
