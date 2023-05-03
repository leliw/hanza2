package pl.priv.leliwa.hanza2.invoice.infrastructure.jpa;

import java.util.Optional;
import java.util.UUID;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import pl.priv.leliwa.hanza2.invoice.domain.model.Invoice;
import pl.priv.leliwa.hanza2.invoice.domain.port.InvoiceRepository;

@Repository
@ConditionalOnProperty(name = "spring.application.repository", havingValue = "JPA")
public class InvoiceRepositoryJpa implements InvoiceRepository {

    private InvoiceCrudRepositoryJpa crudRepositoryJpa;

    public InvoiceRepositoryJpa(InvoiceCrudRepositoryJpa crudRepositoryJpa) {
        this.crudRepositoryJpa = crudRepositoryJpa;
    }

    @Override
    public Optional<Invoice> findById(UUID invoiceId) {
        return crudRepositoryJpa.findById(invoiceId);
    }

    @Override
    public Invoice save(Invoice invoice) {
        return crudRepositoryJpa.save(invoice);
    }

}
