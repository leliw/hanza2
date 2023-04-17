package pl.priv.leliwa.hanza2.invoice.domain.usecase;

import java.util.UUID;

import lombok.AllArgsConstructor;
import pl.priv.leliwa.hanza2.invoice.domain.exception.InvoiceNotFoundException;
import pl.priv.leliwa.hanza2.invoice.domain.model.Invoice;
import pl.priv.leliwa.hanza2.invoice.domain.port.InvoiceRepository;


@AllArgsConstructor
public class ShowInvoiceUseCase {
    
    private InvoiceRepository invoiceRepository;

    public Invoice execute(UUID invoiceId) throws InvoiceNotFoundException {
        return this.invoiceRepository.findById(invoiceId).orElseThrow(() -> new InvoiceNotFoundException(invoiceId));
    }
    
}
