package br.com.sg.mechanical.service;

import br.com.sg.mechanical.domain.Vehicle;
import br.com.sg.mechanical.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class VehicleService {

    private VehicleRepository repository;

    @Autowired
    public VehicleService(VehicleRepository repository) {
        this.repository = repository;
    }

    public List<Vehicle> listAll() {
        return this.repository.findAll();
    }

    @Transactional
    public Vehicle saveOne(Vehicle vehicle) {
        return this.repository.save(vehicle);
    }
}