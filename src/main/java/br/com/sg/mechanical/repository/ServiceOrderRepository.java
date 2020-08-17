package br.com.sg.mechanical.repository;


import br.com.sg.mechanical.domain.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {

    List<ServiceOrder> findAllByStatusEquals(ServiceOrder.STATUS status);

    List<ServiceOrder> findAllByVehicleLicensePlateEquals(String plate);

    List<ServiceOrder> findAllByVehicle_ClientCpfEquals(String cpf);

    List<ServiceOrder> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    List<ServiceOrder> findAllByEmployeeNameContains(String prefix);
 }
