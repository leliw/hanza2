package pl.priv.leliwa.hanza2.invoice.domain.usecase;

import java.util.UUID;

import lombok.AllArgsConstructor;
import pl.priv.leliwa.hanza2.invoice.domain.exception.InvoiceNotFoundException;
import pl.priv.leliwa.hanza2.invoice.domain.model.Invoice;
import pl.priv.leliwa.hanza2.invoice.domain.port.InvoiceRepository;

@AllArgsConstructor
public class RemoveInvoiceItemUseCase {

    private InvoiceRepository invoiceRepository;

    public void execute(UUID invoiceId, Integer no) throws Exception {
        Invoice invoice = this.invoiceRepository.findById(invoiceId).orElseThrow(() ->new InvoiceNotFoundException(invoiceId));
        invoice.remove(no);
        this.invoiceRepository.save(invoice);
    }

}
