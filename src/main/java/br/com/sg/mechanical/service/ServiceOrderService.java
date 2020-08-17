package br.com.sg.mechanical.service;

import br.com.sg.mechanical.domain.ServiceOrder;
import br.com.sg.mechanical.repository.ServiceOrderRepository;
import br.com.sg.mechanical.utils.WorkingTimeTranslatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static br.com.sg.mechanical.domain.ServiceOrder.STATUS.*;

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
        serviceOrder.setUpTime(WorkingTimeTranslatorUtils.translateToTime(serviceOrder.getCreatedAt()));
        return this.repository.save(serviceOrder);
    }

    @Transactional
    public ServiceOrder updateOne(Long id, ServiceOrder serviceOrder) {
        ServiceOrder updatable = this.repository.findById(id).get();
        if (Objects.isNull(updatable.getIdleTime())) {
            updatable.setIdleTime(Duration.ZERO);
        }
        if (!(updatable.getStatus().equals(CANCELED) || updatable.getStatus().equals(FINISHED))) {
            updatable.setUpTime(WorkingTimeTranslatorUtils.translateToTime(updatable.getCreatedAt()));
        }

        /*Não foi alterado o estado*/
        if ((updatable.getStatus().equals(serviceOrder.getStatus())) &&
                /*e o estado atual não corresponde a um estado de uptime*/
           !(updatable.getStatus().equals(IN_ANALYSIS) || updatable.getStatus().equals(IN_PROGRESS)))
        {
            Duration durationToUpdate = WorkingTimeTranslatorUtils.sum(updatable.getDurationIdleTime(), WorkingTimeTranslatorUtils.translateToTime(updatable.getUpdateAt()));
            updatable.setIdleTime(durationToUpdate);
        }
        if (!updatable.getDescription().equals(serviceOrder.getDescription())) {
            updatable.setDescription(serviceOrder.getDescription());
        }
        if (!updatable.getAnalysisResult().equals(serviceOrder.getAnalysisResult())) {
            updatable.setAnalysisResult(serviceOrder.getAnalysisResult());
        }
        if (!updatable.getEmployee().equals(serviceOrder.getEmployee())) {
            updatable.setEmployee(serviceOrder.getEmployee());
        }
        if (!updatable.getStatus().equals(serviceOrder.getStatus())) {
            updatable.setStatus(serviceOrder.getStatus());
        }

        return this.repository.saveAndFlush(updatable);
    }

    public List<ServiceOrder> findAllByDateBetween(LocalDateTime start, LocalDateTime end) {
        return this.repository.findAllByCreatedAtBetween(start,end);
    }

    public List<ServiceOrder> findByClientCpf(String cpf) {
        return this.repository.findAllByVehicle_ClientCpfEquals(cpf);
    }

    public List<ServiceOrder> findByPlate(String plate) {
        return this.repository.findAllByVehicleLicensePlateEquals(plate);
    }

    public List<ServiceOrder> findByStatus(ServiceOrder.STATUS status) {
        return this.repository.findAllByStatusEquals(status);
    }

    public List<ServiceOrder> findByEmployeeName(String prefix) {
        return this.repository.findAllByEmployeeNameContainsIgnoreCase(prefix);
    }
    public Boolean exists(Long id) {
        return this.repository.existsById(id);
    }


}
