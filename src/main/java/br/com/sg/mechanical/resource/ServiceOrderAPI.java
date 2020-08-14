package br.com.sg.mechanical.resource;

import br.com.sg.mechanical.domain.ServiceOrder;
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
