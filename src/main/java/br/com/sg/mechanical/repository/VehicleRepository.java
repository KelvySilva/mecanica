package br.com.sg.mechanical.repository;

import br.com.sg.mechanical.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

}
