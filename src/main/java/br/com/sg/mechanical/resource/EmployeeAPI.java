package br.com.sg.mechanical.resource;

import br.com.sg.mechanical.domain.Employee;
import br.com.sg.mechanical.utils.MessageUtils;
import br.com.sg.mechanical.service.EmployeeService;
import br.com.sg.mechanical.utils.PasswordEncoder;
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

@Api("Employee API")
@RestController
@RequestMapping("v1")
public class EmployeeAPI {

    private EmployeeService service;

    @Autowired
    public EmployeeAPI(EmployeeService service) {
        this.service = service;
    }

    @ApiOperation(value = "Lista todos os funcionários.", response = Employee[].class, responseContainer = "List",consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Nenhum funcionário cadastrado."),
            @ApiResponse(code = 200,message = "Listagem de funcionários")
    })
    @GetMapping(path = "/protected/employees")
    public ResponseEntity listAll() {
        List<Employee> all = this.service.findAll();
        if (all.isEmpty()) return ResponseEntity.ok(
                MessageUtils.createResourceListIsEmptyMessage()
        );
        else return ResponseEntity.ok(all);
    }

    @ApiOperation(value = "Encontra um funcionário pelo seu ID.", response = Employee.class,consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Quando não for possível encontrar o funcionário informado"),
            @ApiResponse(code = 200,message = "Funcionário buscado.")
    })
    @GetMapping(path = "/protected/employee/{id}")
    public ResponseEntity findOne(@PathVariable Long id) {
        Optional<Employee> one = this.service.findOne(id);
        if (one.isPresent()) return ResponseEntity.ok(one.get());
        else return ResponseEntity.ok(MessageUtils.createResourceEntityNotPresentMessage(id));
    }

    @ApiOperation(value = "Cria um novo funcionário.",  response = Employee.class,consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Quando algumas das informações necessárias for vazia ou nula ou o cpf informado já estiver cadastrado."),
            @ApiResponse(code = 200,message = "Funcionário criado.")
    })
    @PostMapping(path = "/admin/employee")
    public ResponseEntity saveOne(@RequestBody @Valid Employee employee, @ApiIgnore Errors errors) {

        if (errors.getFieldErrorCount() > 0)  return ResponseEntity.ok(MessageUtils.createFieldErrorMessage(errors));
        employee.setPassword(PasswordEncoder.encode(employee.getPassword()));
        try {
            Employee employeeStored = this.service.saveOne(employee);
            return ResponseEntity.ok(employeeStored);
        }catch (DataIntegrityViolationException ex) {
            return ResponseEntity.ok(MessageUtils.createDataIntegrityErrorMessage(ex));
        }
    }

    @ApiOperation(value = "Atualiza os dados de um funcionário", response = Employee.class,consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Quando não for possível encontrar o funcionário informado"),
            @ApiResponse(code = 400, message = "Quando os parametros informados estiverem em desacordo;algumas das informações necessárias for vazia ou nula ou o cpf informado já existir."),
            @ApiResponse(code = 200,message = "Funcionário atualizado.")

    })
    @PutMapping(path = "/admin/employee/{id}")
    public ResponseEntity updateOne(@PathVariable Long id, @RequestBody @Valid Employee employee,@ApiIgnore Errors errors) {
        if (id != employee.getId()) {
            return ResponseEntity.ok(MessageUtils.createRequestConflictErrorMessage());
        }
        if (errors.getFieldErrorCount() > 0) {
            return ResponseEntity.ok(MessageUtils.createFieldErrorMessage(errors));
        }
        if (!this.service.exists(id)) {
            return ResponseEntity.ok(MessageUtils.createResourceEntityNotPresentMessage(id));
        }
        return ResponseEntity.ok(this.service.updateOne(id, employee));
    }

    @ApiOperation(value = "Remove um funcionário.",consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Quando não for possível encontrar o funcionário informado"),
            @ApiResponse(code = 200, message = "Funcionário deletado.")
    })
    @DeleteMapping(path = "/admin/employee/{id}")
    public ResponseEntity deleteOne(@ApiParam(value = "Id do funcionário", required = true, example = "3")  @PathVariable Long id) {
        if (!this.service.findOne(id).isPresent()) return ResponseEntity.ok(MessageUtils.createResourceEntityNotPresentMessage(id));
        this.service.deleteOne(id);
        return ResponseEntity.ok( MessageUtils.createDeleteSuccessMessage("Funcionário"));
    }

}
