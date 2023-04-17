package pl.priv.leliwa.hanza2.invoice.domain.port;

import java.util.Optional;
import java.util.UUID;

import pl.priv.leliwa.hanza2.invoice.domain.model.Invoice;

public interface InvoiceRepository {
    Optional<Invoice> findById(UUID invoiceId);
    Optional<Invoice> findByNumber(String number);
    void save(Invoice invoice);
}
