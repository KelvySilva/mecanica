package br.com.sg.mechanical.service;

import br.com.sg.mechanical.domain.Quote;
import br.com.sg.mechanical.domain.ServiceOrder;
import br.com.sg.mechanical.repository.QuoteRepository;
import br.com.sg.mechanical.repository.ServiceOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class QuoteService {

    private QuoteRepository repository;
    private ServiceOrderRepository orderRepository;

    @Autowired
    public QuoteService(QuoteRepository repository, ServiceOrderRepository orderRepository) {
        this.repository = repository;
        this.orderRepository = orderRepository;
    }

    public List<Quote> listAll() {
        return this.repository.findAll();
    }

    public Optional<Quote> findOne(Long id) {
        return this.repository.findById(id);
    }

    @Transactional
    public Quote saveOne(Quote quote) {
        Quote quoteToSave = this.resolveQuote(quote);
        return this.repository.save(quoteToSave);
    }

    @Transactional
    public Quote updateOne(Long id, Quote quote) {
        Quote storedQuote = this.repository.findById(id).get();
        Quote quoteToUpdate = this.resolveQuote(storedQuote);
        if (!quoteToUpdate.getStatus().equals(quote.getStatus())) {
            quoteToUpdate.setStatus(quote.getStatus());
        }
        return this.repository.saveAndFlush(quoteToUpdate);
    }


    public Boolean exists(Long id) {
        return this.repository.existsById(id);
    }

    private Quote resolveQuote(Quote quote) {
        quote.setSubtotal(quote.getItems()
                .stream()
                .map(item -> {
                    item.setSubtotal(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                    return item.getSubtotal();
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        quote.setDiscount(quote.getItems()
                .stream()
                .map(item -> {
                    item.setTotal(item.getSubtotal().subtract(item.getDiscount()));
                    return item.getDiscount();
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        quote.setTotal(quote.getSubtotal().subtract(quote.getDiscount()));
        return quote;
    }

}
