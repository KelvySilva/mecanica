package br.com.sg.mechanical.repository;

import br.com.sg.mechanical.domain.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

    List<Quote> findAllByServiceOrder_Vehicle_ClientId(Long id);

    List<Quote> findAllByServiceOrder_Vehicle_ClientCpfEquals(String cpf);

    List<Quote> findAllByServiceOrder_Vehicle_ClientNameStartsWith(String prefix);

    List<Quote> findAllByServiceOrder_Vehicle_ClientNameContains(String prefix);

    List<Quote> findAllByServiceOrder_VehicleLicensePlateStartsWith(String prefix);

    List<Quote> findAllByServiceOrder_VehicleLicensePlateEquals(String prefix);
}
