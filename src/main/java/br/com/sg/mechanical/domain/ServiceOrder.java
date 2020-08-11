package br.com.sg.mechanical.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class ServiceOrder extends AbstractEntity {

    private StringBuilder description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    @Enumerated(EnumType.STRING)
    private STATUS status;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Employee employee;


    public enum STATUS {

        IN_ANALYSYS("EM_ANALISE"),
        AWAITING_APPROVAL("AGUARDANDO_APROVACAO"),
        APPROVED("APROVADO"),
        IN_PROGRESS("EM_ANDAMENTO"),
        FINNISHED("FINALIZADO"),
        CALCELED("CANCELADO");

        private String desc;

        STATUS(String desc) {
            this.desc = desc;
        }
    }

}
