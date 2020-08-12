package br.com.sg.mechanical.repository;


import br.com.sg.mechanical.domain.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {
}
