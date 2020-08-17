package br.com.sg.mechanical.resource;

import br.com.sg.mechanical.domain.Client;
import br.com.sg.mechanical.domain.ServiceOrder;
import br.com.sg.mechanical.domain.Vehicle;
import br.com.sg.mechanical.error.MessageDetails;
import br.com.sg.mechanical.utils.MessageUtils;
import br.com.sg.mechanical.service.ServiceOrderService;
import br.com.sg.mechanical.utils.WorkingTimeTranslatorUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Api("ServiceOrder API")
@RestController
@RequestMapping("v1")
public class ServiceOrderAPI {

    private ServiceOrderService service;

    @Autowired
    public ServiceOrderAPI(ServiceOrderService service) {
        this.service = service;
    }


    @ApiOperation(value = "Lista todas as OS's.", response = ServiceOrder[].class, responseContainer = "List",consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Nenhuma OS cadastrada."),
            @ApiResponse(code = 200,message = "Listagem de OS's")
    })
    @GetMapping(path = "/protected/os")
    public ResponseEntity listAll() {
        List<ServiceOrder> serviceOrders = this.service.listAll();
        if (serviceOrders.isEmpty()) return ResponseEntity.ok(MessageUtils.createResourceListIsEmptyMessage());
        else return ResponseEntity.ok(serviceOrders);
    }

    @ApiOperation(value = "Lista todas as OS's entre o período informado.",notes = "Utilizar formato yyyy-MM-dd", response = ServiceOrder[].class, responseContainer = "List",consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Nenhuma OS cadastrada."),
            @ApiResponse(code = 200,message = "Listagem de OS's")
    })
    @GetMapping(path = "/protected/os/period/{startDate}/{endDate}")
    public ResponseEntity listAllByPeriod(@PathVariable String startDate, @PathVariable String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<ServiceOrder> serviceOrders = this.service.findAllByDateBetween(LocalDateTime.of(start, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MIN));
        if (serviceOrders.isEmpty()) return ResponseEntity.ok(MessageUtils.createResourceListIsEmptyMessage());
        else return ResponseEntity.ok(serviceOrders);
    }

    @ApiOperation(value = "Lista todas as OS's pelo cpf do cliente",notes = "Informe o cpf como cadastrado", response = ServiceOrder[].class, responseContainer = "List",consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Nenhuma OS cadastrada."),
            @ApiResponse(code = 200,message = "Listagem de OS's")
    })
    @GetMapping(path = "/protected/os/client/{cpf}")
    public ResponseEntity listAllByClientCpf(@PathVariable String cpf) {
        List<ServiceOrder> serviceOrders = this.service.findByClientCpf(cpf);
        if (serviceOrders.isEmpty()) return ResponseEntity.ok(MessageUtils.createResourceListIsEmptyMessage());
        else return ResponseEntity.ok(serviceOrders);
    }

    @ApiOperation(value = "Lista todas as OS's pela placa do veículo",notes = "Informe a placa como cadastrada", response = ServiceOrder[].class, responseContainer = "List",consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Nenhuma OS cadastrada."),
            @ApiResponse(code = 200,message = "Listagem de OS's")
    })
    @GetMapping(path = "/protected/os/vehicle/{plate}")
    public ResponseEntity listAllByVehiclePlate(@PathVariable String plate) {
        List<ServiceOrder> serviceOrders = this.service.findByPlate(plate);
        if (serviceOrders.isEmpty()) return ResponseEntity.ok(MessageUtils.createResourceListIsEmptyMessage());
        else return ResponseEntity.ok(serviceOrders);
    }

    @ApiOperation(value = "Lista todas as OS's pelo status informado",response = ServiceOrder[].class, responseContainer = "List",notes = "Valores possíveis: IN_ANALYSIS, ANALYSED, AWAITING_ANALYSIS, AWAITING_APPROVAL, APPROVED, IN_PROGRESS, FINISHED ,CANCELED",consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Nenhuma OS cadastrada."),
            @ApiResponse(code = 200,message = "Listagem de OS's")
    })
    @GetMapping(path = "/protected/os/status/{status}")
    public ResponseEntity listAllByStatus(@PathVariable ServiceOrder.STATUS status) {
        List<ServiceOrder> serviceOrders = this.service.findByStatus(status);
        if (serviceOrders.isEmpty()) return ResponseEntity.ok(MessageUtils.createResourceListIsEmptyMessage());
        else return ResponseEntity.ok(serviceOrders);
    }

    @ApiOperation(value = "Lista todas as OS's pelo nome do funcuinário informado",response = ServiceOrder[].class, responseContainer = "List",consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Nenhuma OS cadastrada."),
            @ApiResponse(code = 200,message = "Listagem de OS's")
    })
    @GetMapping(path = "/protected/os/employee")
    public ResponseEntity listAllByElployeeName(@RequestParam String name) {
        List<ServiceOrder> serviceOrders = this.service.findByEmployeeName(name);
        if (serviceOrders.isEmpty()) return ResponseEntity.ok(MessageUtils.createResourceListIsEmptyMessage());
        else return ResponseEntity.ok(serviceOrders);
    }


    @ApiOperation(value = "Encontra uma OS pelo seu ID.", response = ServiceOrder.class,consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Quando não for possível encontrar a OS informado"),
            @ApiResponse(code = 200,message = "OS buscada.")
    })
    @GetMapping(path = "/protected/os/{id}")
    public ResponseEntity findOne( @ApiParam(value = "Id da ordem de serviço", required = true, example = "3") @PathVariable Long id) {
        Optional<ServiceOrder> one = this.service.findOne(id);
        System.out.println(WorkingTimeTranslatorUtils.translateToTime(one.get().getCreatedAt()));
        if (!one.isPresent()) return ResponseEntity.ok(MessageUtils.createResourceEntityNotPresentMessage(id));
        else return ResponseEntity.ok(one);
    }


    @ApiOperation(value = "Cria uma nova OS",  response = ServiceOrder.class,consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Quando algumas das informações necessárias for vazia ou nula."),
            @ApiResponse(code = 200,message = "OS criada.")
    })

    @PostMapping(path = "/admin/os")
    public ResponseEntity saveOne(@RequestBody @Valid ServiceOrder serviceOrder,@ApiIgnore Errors errors) {
        if (errors.getFieldErrorCount() > 0) return ResponseEntity.ok(MessageUtils.createFieldErrorMessage(errors));
        return ResponseEntity.ok(this.service.saveOne(serviceOrder));
    }

    @ApiOperation(value = "Atualiza os dados de uma OS", response = ServiceOrder.class,consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Quando não for possível encontrar a OS informado"),
            @ApiResponse(code = 400, message = "Quando os parametros informados estiverem em desacordo;algumas das informações necessárias for vazia ou nula"),
            @ApiResponse(code = 200,message = "OS atualizada.")

    })
    @PutMapping(path = "/admin/os/{id}")
    public ResponseEntity updateOne(@ApiParam(value = "Id da ordem de serviço", required = true, example = "3") @PathVariable Long id, @RequestBody @Valid ServiceOrder serviceOrder,@ApiIgnore Errors errors) {
        if (errors.getFieldErrorCount() > 0) return ResponseEntity.ok(MessageUtils.createFieldErrorMessage(errors));
        if (id != serviceOrder.getId()) return ResponseEntity.ok(MessageUtils.createRequestConflictErrorMessage());
        if (!this.service.exists(id)) return ResponseEntity.ok(MessageUtils.createResourceEntityNotPresentMessage(id));
        else return ResponseEntity.ok(this.service.updateOne(id, serviceOrder));
    }

    @ApiOperation(value = "Remove uma OS.", consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Quando não for possível encontrar a OS informada"),
            @ApiResponse(code = 200, message = "OS deletada.")
    })
    @DeleteMapping(path = "/admin/os/{id}")
    public ResponseEntity deleteOne(@ApiParam(value = "Id da OS", required = true, example = "3") @PathVariable Long id) {
        if(!this.service.findOne(id).isPresent()) return ResponseEntity.ok(MessageUtils.createResourceEntityNotPresentMessage(id));
        else return ResponseEntity.ok(MessageUtils.createDeleteSuccessMessage("OS"));
    }

}
