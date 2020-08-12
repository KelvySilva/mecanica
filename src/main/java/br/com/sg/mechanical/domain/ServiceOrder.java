package br.com.sg.mechanical.domain;

import br.com.sg.mechanical.constants.ValidationMessages;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class ServiceOrder extends AbstractEntity {

    @NotNull(message = ValidationMessages.NOT_NULL_MESSAGE)
    @NotEmpty(message = ValidationMessages.NOT_EMPTY_MESSAGE)
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
