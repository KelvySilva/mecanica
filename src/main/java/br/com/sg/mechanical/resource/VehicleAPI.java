package br.com.sg.mechanical.resource;

import br.com.sg.mechanical.domain.Vehicle;
import br.com.sg.mechanical.error.ErrorDetails;
import br.com.sg.mechanical.service.VehicleService;
import br.com.sg.mechanical.utils.FieldErrorMessageFormater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
        if (vehicles.isEmpty()) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok(vehicles);
    }

    @PostMapping(path = "/admin/vehicle")
    public ResponseEntity saveOne(@RequestBody @Valid Vehicle vehicle, Errors errors) {

        if (errors.getFieldErrorCount() > 0) {
            String errorMessage = FieldErrorMessageFormater.format(errors);
            return ResponseEntity.ok(
                    ErrorDetails.Builder.anErrorDetails()
                            .title("Erro de validação")
                            .status(404)
                            .timestamp(Date.from(Instant.now()).getTime())
                            .detail(String.format(errorMessage))
                            .developerMessage("DEVELOPMENT")
                            .build()
            );
        }

        try {
            Vehicle vehicleStored = this.service.saveOne(vehicle);
            if (Objects.nonNull(vehicle) && vehicleStored instanceof  Vehicle) {
                return ResponseEntity.ok(vehicleStored);
            } else {
                return ResponseEntity.ok("Error");
            }
        }catch (DataIntegrityViolationException ex) {
            return ResponseEntity.ok(
                    ErrorDetails.Builder.anErrorDetails()
                            .title("Erro de validação")
                            .status(404)
                            .timestamp(Date.from(Instant.now()).getTime())
                            .detail("lisencePlate deve ser unico.")
                            .developerMessage("DEVELOPMENT")
                            .build()
            );
        }

    }


}
