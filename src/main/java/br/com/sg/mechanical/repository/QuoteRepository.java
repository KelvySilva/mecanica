package br.com.sg.mechanical.repository;

import br.com.sg.mechanical.domain.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
}
