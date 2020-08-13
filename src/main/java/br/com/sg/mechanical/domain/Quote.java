package br.com.sg.mechanical.domain;

import br.com.sg.mechanical.constants.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Quote extends AbstractEntity{

    @NotNull(message= ValidationMessages.NOT_NULL_MESSAGE)
    @NotEmpty(message= ValidationMessages.NOT_EMPTY_MESSAGE)
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<QuoteItem> items;

    private BigDecimal subtotal = BigDecimal.ZERO;

    private BigDecimal total = BigDecimal.ZERO;

    private BigDecimal discount = BigDecimal.ZERO;

    @NotNull(message= ValidationMessages.NOT_NULL_MESSAGE)
    @OneToOne(fetch = FetchType.EAGER)
    private ServiceOrder serviceOrder;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'OPENED'")
    private STATUS status;

    public enum STATUS {

        APPROVED("APROVADO"),
        CANCELED("CANCELADO"),
        OPENED("ABERTO"),
        CLOSED("FECHADO");

        private String desc;
        STATUS(String desc) {
            this.desc = desc;
        }
    }

}
