package br.com.sg.mechanical.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class QuoteItem extends AbstractEntity {

    private StringBuilder description;

    private BigDecimal unitPrice = BigDecimal.ZERO;

    private BigDecimal subtotal = BigDecimal.ZERO;

    private BigDecimal total = BigDecimal.ZERO;

    private BigDecimal discount = BigDecimal.ZERO;
}
