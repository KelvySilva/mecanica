package br.com.sg.mechanical.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Quote extends AbstractEntity{

    @ManyToMany
    List<QuoteItem> items;

    private BigDecimal unitPrice = BigDecimal.ZERO;

    private BigDecimal subtotal = BigDecimal.ZERO;

    private BigDecimal total = BigDecimal.ZERO;

    private BigDecimal discount = BigDecimal.ZERO;

    private ServiceOrder serviceOrder;

}
