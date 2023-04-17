package pl.priv.leliwa.hanza2.invoice.domain.usecase;

import lombok.AllArgsConstructor;
import pl.priv.leliwa.hanza2.invoice.domain.model.Invoice;
import pl.priv.leliwa.hanza2.invoice.domain.port.InvoiceRepository;


@AllArgsConstructor
public class CreateInvoiceUseCase {
    
    private InvoiceRepository invoiceRepository;

    public Invoice execute(Invoice invoice) {
        this.invoiceRepository.save(invoice);
        return invoice;
    }
    
}
