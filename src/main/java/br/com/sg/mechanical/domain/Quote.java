package br.com.sg.mechanical.domain;

import br.com.sg.mechanical.constants.ValidationMessages;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@ApiModel("Objeto Orçamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Quote extends AbstractEntity{

    @NotNull(message= ValidationMessages.NOT_NULL_MESSAGE)
    @NotEmpty(message= ValidationMessages.NOT_EMPTY_MESSAGE)
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<QuoteItem> items;

    @ApiModelProperty(value = "Subtotal do orçamento",required = false, notes = "Opcional")
    private BigDecimal subtotal = BigDecimal.ZERO;

    @ApiModelProperty(value = "Total do orçamento",required = false, notes = "Opcional")
    private BigDecimal total = BigDecimal.ZERO;

    @ApiModelProperty(value = "Total dos descontos do orçamento", required = false, notes = "Opcional")
    private BigDecimal discount = BigDecimal.ZERO;

    @NotNull(message= ValidationMessages.NOT_NULL_MESSAGE)
    @OneToOne(fetch = FetchType.EAGER)
    private ServiceOrder serviceOrder;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'OPENED'")
    private STATUS status;

    @ApiModel
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
