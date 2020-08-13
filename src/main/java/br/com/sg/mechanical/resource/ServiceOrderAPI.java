package br.com.sg.mechanical.resource;

import br.com.sg.mechanical.domain.ServiceOrder;
import br.com.sg.mechanical.factory.ErrorFactory;
import br.com.sg.mechanical.service.ServiceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1")
public class ServiceOrderAPI {

    private ServiceOrderService service;

    @Autowired
    public ServiceOrderAPI(ServiceOrderService service) {
        this.service = service;
    }

    @GetMapping(path = "/protected/os")
    public ResponseEntity listAll() {
        List<ServiceOrder> serviceOrders = this.service.listAll();
        if (serviceOrders.isEmpty()) return ResponseEntity.ok(ErrorFactory.createResourceListIsEmptyMessage());
        else return ResponseEntity.ok(serviceOrders);
    }

    @GetMapping(path = "/protected/os/{id}")
    public ResponseEntity findOne(@PathVariable Long id) {
        Optional<ServiceOrder> one = this.service.findOne(id);
        if (!one.isPresent()) return ResponseEntity.ok(ErrorFactory.createResourceEntityNotPresentMessage(id));
        else return ResponseEntity.ok(one);
    }

    @PostMapping(path = "/admin/os")
    public ResponseEntity saveOne(@RequestBody @Valid ServiceOrder serviceOrder, Errors errors) {
        if (errors.getFieldErrorCount() > 0) return ResponseEntity.ok(ErrorFactory.createFieldErrorMessage(errors));
        return ResponseEntity.ok(serviceOrder);
    }

    @PutMapping(path = "/admin/os/{id}")
    public ResponseEntity updateOne(@PathVariable Long id, @RequestBody @Valid ServiceOrder serviceOrder, Errors errors) {
        if (errors.getFieldErrorCount() > 0) return ResponseEntity.ok(ErrorFactory.createFieldErrorMessage(errors));
        if (id != serviceOrder.getId()) return ResponseEntity.ok(ErrorFactory.createRequestConflictErrorMessage());
        if (!this.service.exists(id)) return ResponseEntity.ok(ErrorFactory.createResourceEntityNotPresentMessage(id));
        else return ResponseEntity.ok(this.service.updateOne(id, serviceOrder));
    }
}