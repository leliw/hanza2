package pl.priv.leliwa.hanza2.invoice.domain.usecase;

import java.util.UUID;

import lombok.AllArgsConstructor;
import pl.priv.leliwa.hanza2.invoice.domain.model.Invoice;
import pl.priv.leliwa.hanza2.invoice.domain.port.InvoiceRepository;

@AllArgsConstructor
public class CreateInvoiceUseCase {

    private InvoiceRepository invoiceRepository;

    public Invoice execute(Invoice src) {
        // Skopiowanie faktury i nadanie nowego Id
        Invoice invoice = src.toBuilder()
                .id(UUID.randomUUID())
                .build();
        this.invoiceRepository.save(invoice);
        return invoice;
    }

}
