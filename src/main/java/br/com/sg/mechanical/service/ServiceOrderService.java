package br.com.sg.mechanical.service;

import br.com.sg.mechanical.domain.ServiceOrder;
import br.com.sg.mechanical.repository.ServiceOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Boolean exists(Long id) {
        return this.repository.existsById(id);
    }


}
