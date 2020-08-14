package br.com.sg.mechanical.resource;

import br.com.sg.mechanical.domain.Client;
import br.com.sg.mechanical.domain.Vehicle;
import br.com.sg.mechanical.utils.MessageUtils;
import br.com.sg.mechanical.service.VehicleService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Api("Vehicle API")
@RestController
@RequestMapping("v1")
public class VehicleAPI {

    private VehicleService service;

    @Autowired
    public VehicleAPI(VehicleService service) {
        this.service = service;
    }

    @ApiOperation(value = "Lista todos os veículos.", response = Vehicle[].class, responseContainer = "List",consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Nenhum veículo cadastrado."),
            @ApiResponse(code = 200,message = "Listagem de veículos")
    })
    @GetMapping(path = "/protected/vehicles")
    public ResponseEntity listAll() {
        List<Vehicle> vehicles = this.service.listAll();
        if (vehicles.isEmpty()) return ResponseEntity.ok(MessageUtils.createResourceListIsEmptyMessage());
        else return ResponseEntity.ok(vehicles);
    }

    @ApiOperation(value = "Encontra um veículo pelo seu ID.", response = Vehicle.class,consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Quando não for possível encontrar o veículo informado"),
            @ApiResponse(code = 200,message = "Veículo buscado.")
    })
    @GetMapping(path = "/protected/vehicle/{id}")
    public ResponseEntity findOne(@ApiParam(value = "Id do veículo", required = true, example = "3") @PathVariable Long id) {
        Optional<Vehicle> one = this.service.findOne(id);
        if (one.isPresent()) return ResponseEntity.ok(one);
        else return ResponseEntity.ok(MessageUtils.createResourceEntityNotPresentMessage(id));
    }


    @ApiOperation(value = "Cria um novo veículo.",  response = Vehicle.class,consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Quando algumas das informações necessárias for vazia ou nula."),
            @ApiResponse(code = 200,message = "Veículo criado.")
    })
    @PostMapping(path = "/admin/vehicle")
    public ResponseEntity saveOne(@RequestBody @Valid Vehicle vehicle,@ApiIgnore Errors errors) {
        if (errors.getFieldErrorCount() > 0) return ResponseEntity.ok(MessageUtils.createFieldErrorMessage(errors));
        try {
            Vehicle vehicleStored = this.service.saveOne(vehicle);
            return ResponseEntity.ok(vehicleStored);
        }catch (DataIntegrityViolationException ex) {
            return ResponseEntity.ok(MessageUtils.createDataIntegrityErrorMessage(ex));
        }
    }

    @ApiOperation(value = "Atualiza os dados de um veículo", response = Client.class,consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Quando não for possível encontrar o veículo informado"),
            @ApiResponse(code = 400, message = "Quando os parametros informados estiverem em desacordo;algumas das informações necessárias for vazia ou nula ou o cpf informado já existir."),
            @ApiResponse(code = 200,message = "Veículo atualizado.")

    })
    @PutMapping(path = "/admin/vehicle/{id}")
    public ResponseEntity updateOne(@ApiParam(value = "Id do veículo", required = true, example = "3") @PathVariable Long id, @RequestBody @Valid Vehicle vehicle,@ApiIgnore Errors errors) {
        if (id != vehicle.getId()) return ResponseEntity.ok(MessageUtils.createRequestConflictErrorMessage());
        if (errors.getFieldErrorCount() > 0) return ResponseEntity.ok(MessageUtils.createFieldErrorMessage(errors));
        if (!this.service.findOne(id).isPresent()) return ResponseEntity.ok(MessageUtils.createResourceEntityNotPresentMessage(id));
        else return ResponseEntity.ok(this.service.updateOne(id,vehicle));
    }

    @ApiOperation(value = "Remove um veículo.",consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Quando não for possível encontrar o veículo informado"),
            @ApiResponse(code = 200, message = "Veículo deletado.")
    })
    @DeleteMapping(path = "/admin/vehicle/{id}")
    public ResponseEntity deleteOne(@ApiParam(value = "Id do veículo", required = true, example = "3") @PathVariable Long id) {
        if (!this.service.findOne(id).isPresent()) return ResponseEntity.ok(MessageUtils.createResourceEntityNotPresentMessage(id));
        this.service.deleteOne(id);
        return ResponseEntity.ok(MessageUtils.createDeleteSuccessMessage("Veículo"));
    }


}
