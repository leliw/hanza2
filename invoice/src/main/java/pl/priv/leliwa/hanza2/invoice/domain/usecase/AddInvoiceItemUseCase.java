package pl.priv.leliwa.hanza2.invoice.domain.usecase;

import java.util.UUID;

import lombok.AllArgsConstructor;
import pl.priv.leliwa.hanza2.invoice.domain.exception.InvoiceNotFoundException;
import pl.priv.leliwa.hanza2.invoice.domain.model.Invoice;
import pl.priv.leliwa.hanza2.invoice.domain.model.InvoiceItem;
import pl.priv.leliwa.hanza2.invoice.domain.port.InvoiceRepository;


@AllArgsConstructor
public class AddInvoiceItemUseCase {

    private InvoiceRepository invoiceRepository;

    public void execute(UUID invoiceId, InvoiceItem invoiceItem) throws InvoiceNotFoundException {
        Invoice invoice = this.invoiceRepository.findById(invoiceId).orElseThrow(() -> new InvoiceNotFoundException(invoiceId));
        invoice.add(invoiceItem);
        this.invoiceRepository.save(invoice);
    }
    
}
