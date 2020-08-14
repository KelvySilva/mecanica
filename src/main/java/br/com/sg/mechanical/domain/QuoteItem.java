package br.com.sg.mechanical.domain;

import br.com.sg.mechanical.constants.ValidationMessages;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@ApiModel(value = "Objeto Item de Orçamento", description = "Peças ou Serviços")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class QuoteItem extends AbstractEntity {

    @ApiModelProperty("Descrição do produto/peça/serviço")
    @NotNull(message = ValidationMessages.NOT_NULL_MESSAGE)
    private String description;

    @ApiModelProperty("Preço unitário")
    @NotNull(message = ValidationMessages.NOT_NULL_MESSAGE)
    private BigDecimal unitPrice = BigDecimal.ZERO;

    @ApiModelProperty("Quntidade")
    @NotNull(message = ValidationMessages.NOT_NULL_MESSAGE)
    private Integer quantity = 0;

    @ApiModelProperty(value = "Subitotal do item", notes = "Opcional")
    private BigDecimal subtotal = BigDecimal.ZERO;

    @ApiModelProperty(value = "Total do item", notes = "Opcional")
    private BigDecimal total = BigDecimal.ZERO;

    @ApiModelProperty(value = "Desconto do item")
    private BigDecimal discount = BigDecimal.ZERO;
}
