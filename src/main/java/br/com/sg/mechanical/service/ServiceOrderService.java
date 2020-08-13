package br.com.sg.mechanical.service;

import br.com.sg.mechanical.domain.ServiceOrder;
import br.com.sg.mechanical.repository.ServiceOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceOrderService {

    private ServiceOrderRepository repository;

    @Autowired
    public ServiceOrderService(ServiceOrderRepository repository) {
        this.repository = repository;
    }

    public List<ServiceOrder> listAll() {
        return this.repository.findAll();
    }

    public Optional<ServiceOrder> findOne(Long id) {
        return this.repository.findById(id);
    }

    @Transactional
    public ServiceOrder saveOne(ServiceOrder serviceOrder) {
        return this.repository.save(serviceOrder);
    }

    @Transactional
    public ServiceOrder updateOne(Long id, ServiceOrder serviceOrder) {
        ServiceOrder updatable = this.repository.findById(id).get();

        if (!updatable.getClient().equals(serviceOrder.getClient())) {
            updatable.setClient(serviceOrder.getClient());
        }
        if (!updatable.getDescription().equals(serviceOrder.getDescription())) {
            updatable.setDescription(serviceOrder.getDescription());
        }
        if (!updatable.getEmployee().equals(serviceOrder.getEmployee())) {
            updatable.setEmployee(serviceOrder.getEmployee());
        }
        if (!updatable.getStatus().equals(serviceOrder.getStatus())) {
            updatable.setStatus(serviceOrder.getStatus());
        }

        return this.repository.saveAndFlush(updatable);
    }

    public Boolean exists(Long id) {
        return this.repository.existsById(id);
    }


}
