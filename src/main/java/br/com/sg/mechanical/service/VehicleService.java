package br.com.sg.mechanical.service;

import br.com.sg.mechanical.domain.Vehicle;
import br.com.sg.mechanical.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        if (Objects.nonNull(vehicle.getId())) vehicle.setId(0L);
        return this.repository.save(vehicle);
    }

    public Optional<Vehicle> findOne(Long id) {
        return this.repository.findById(id);
    }
    public Boolean exists(Long id) {
        return this.repository.existsById(id);
    }

    @Transactional
    public Vehicle updateOne(Long id, Vehicle vehicle) {
        Vehicle vehicleToUpdate = this.repository.findById(id).get();
        if (!vehicleToUpdate.getBrand().equals(vehicle.getBrand())) {
            vehicleToUpdate.setBrand(vehicle.getBrand());
        }
        if (!vehicleToUpdate.getManufactureDate().equals(vehicle.getManufactureDate())) {
            vehicleToUpdate.setManufactureDate(vehicle.getManufactureDate());
        }
        if (!vehicleToUpdate.getReleaseDate().equals(vehicle.getReleaseDate())) {
            vehicleToUpdate.setReleaseDate(vehicle.getReleaseDate());
        }
        if (!vehicleToUpdate.getModel().equals(vehicle.getModel())) {
            vehicleToUpdate.setModel(vehicle.getModel());
        }
        if (!vehicleToUpdate.getLicensePlate().equals(vehicle.getLicensePlate())) {
            vehicleToUpdate.setLicensePlate(vehicle.getLicensePlate());
        }
        if (!vehicleToUpdate.getClient().equals(vehicle.getClient())) {
            vehicleToUpdate.setClient(vehicle.getClient());
        }
        return this.repository.saveAndFlush(vehicleToUpdate);
    }

    @Transactional
    public void deleteOne(Long id) {
        this.repository.deleteById(id);
    }
}
