package br.com.sg.mechanical.service;

import br.com.sg.mechanical.domain.Employee;
import br.com.sg.mechanical.repository.EmployeeRepository;
import br.com.sg.mechanical.utils.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private EmployeeRepository repository;

    @Autowired
    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> findAll() {
        return this.repository.findAll();
    }

    public Optional<Employee> findOne(Long id) {
        return this.repository.findById(id);
    }

    @Transactional
    public Employee saveOne(Employee employee) {
        return this.repository.save(employee);
    }

    @Transactional
    public Employee updateOne(Long id, Employee employee) {
        Employee update = this.repository.findById(id).get();
        if (!update.getName().equals(employee.getName())) {
            update.setName(employee.getName());
        }
        if (!update.getAddress().equals(employee.getAddress())) {
            update.setAddress(employee.getAddress());
        }
        if (!update.getAddressNumber().equals(employee.getAddressNumber())) {
            update.setAddressNumber(employee.getAddressNumber());
        }
        if (!update.getState().equals(employee.getState())) {
            update.setState(employee.getState());
        }
        if (!update.getCity().equals(employee.getCity())) {
            update.setCity(employee.getCity());
        }
        if (!update.getCpf().equals(employee.getCpf())) {
            update.setCpf(employee.getCpf());
        }
        if (!update.getUsername().equals(employee.getUsername())) {
            update.setUsername(employee.getUsername());
        }
        if (!update.getPassword().equalsIgnoreCase(employee.getPassword())) {
            update.setPassword(PasswordEncoder.encode(employee.getPassword()));
        }
        if (!update.getType().equals(employee.getType())) {
            update.setType(employee.getType());
        }

        return this.repository.saveAndFlush(update);
    }

    public Boolean exists(Long id) {
        return this.repository.existsById(id);
    }


    @Transactional
    public void deleteOne(Long id) {
        this.repository.deleteById(id);
    }
}
