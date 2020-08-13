package br.com.sg.mechanical.domain;

import br.com.sg.mechanical.constants.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class QuoteItem extends AbstractEntity {

    @NotNull(message = ValidationMessages.NOT_NULL_MESSAGE)
    private StringBuilder description;

    @NotNull(message = ValidationMessages.NOT_NULL_MESSAGE)
    private BigDecimal unitPrice = BigDecimal.ZERO;

    @NotNull(message = ValidationMessages.NOT_NULL_MESSAGE)
    private Integer quantity = 0;

    private BigDecimal subtotal = BigDecimal.ZERO;

    private BigDecimal total = BigDecimal.ZERO;

    private BigDecimal discount = BigDecimal.ZERO;
}
