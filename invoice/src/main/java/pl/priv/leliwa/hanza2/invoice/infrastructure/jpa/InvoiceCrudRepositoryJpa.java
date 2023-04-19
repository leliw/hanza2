package pl.priv.leliwa.hanza2.invoice.infrastructure.jpa;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import pl.priv.leliwa.hanza2.invoice.domain.model.Invoice;

public interface InvoiceCrudRepositoryJpa extends CrudRepository<Invoice, UUID> {
    
}
