package br.com.sg.mechanical.resource;

import br.com.sg.mechanical.domain.Vehicle;
import br.com.sg.mechanical.factory.ErrorFactory;
import br.com.sg.mechanical.service.VehicleService;
import br.com.sg.mechanical.utils.FieldErrorMessageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1")
public class VehicleAPI {

    private VehicleService service;

    @Autowired
    public VehicleAPI(VehicleService service) {
        this.service = service;
    }

    @GetMapping(path = "/protected/vehicles")
    public ResponseEntity listAll() {
        List<Vehicle> vehicles = this.service.listAll();
        if (vehicles.isEmpty()) return ResponseEntity.ok(ErrorFactory.createResourceListIsEmptyMessage());
        else return ResponseEntity.ok(vehicles);
    }
    @GetMapping(path = "/protected/vehicle/{id}")
    public ResponseEntity findOne(@PathVariable Long id) {
        Optional<Vehicle> one = this.service.findOne(id);
        if (one.isPresent()) return ResponseEntity.ok(one);
        else return ResponseEntity.ok(ErrorFactory.createResourceEntityNotPresentMessage(id));
    }


    @PostMapping(path = "/admin/vehicle")
    public ResponseEntity saveOne(@RequestBody @Valid Vehicle vehicle, Errors errors) {
        if (errors.getFieldErrorCount() > 0) {
            return ResponseEntity.ok(ErrorFactory.createFieldErrorMessage(errors));
        }
        try {
            Vehicle vehicleStored = this.service.saveOne(vehicle);
            return ResponseEntity.ok(vehicleStored);
        }catch (DataIntegrityViolationException ex) {
            String constraintField = FieldErrorMessageFormatter.getConstraintField(ex);
            return ResponseEntity.ok(ErrorFactory.createDataIntegrityErrorMessage(constraintField));
        }

    }


}
