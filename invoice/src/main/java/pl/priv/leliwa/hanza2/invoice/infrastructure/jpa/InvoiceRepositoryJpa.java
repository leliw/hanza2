package pl.priv.leliwa.hanza2.invoice.infrastructure.jpa;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.priv.leliwa.hanza2.invoice.domain.model.Invoice;
import pl.priv.leliwa.hanza2.invoice.domain.port.InvoiceRepository;

@Repository
public class InvoiceRepositoryJpa implements InvoiceRepository {

    @Autowired
    private InvoiceCrudRepositoryJpa crudRepositoryJpa;

    @Override
    public Optional<Invoice> findById(UUID invoiceId) {
        return crudRepositoryJpa.findById(invoiceId);
    }

    @Override
    public Invoice save(Invoice invoice) {
        return crudRepositoryJpa.save(invoice);
    }
    
}
