package pl.priv.leliwa.hanza2.invoice.domain.usecase;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import pl.priv.leliwa.hanza2.invoice.domain.model.Invoice;
import pl.priv.leliwa.hanza2.invoice.domain.port.InvoiceRepository;


@AllArgsConstructor
public class CreateInvoiceUseCase {
    
    private InvoiceRepository invoiceRepository;

    public void execute(LocalDate saleDate) {
        Invoice invoice = Invoice.builder().saleDate(saleDate).build();
        this.invoiceRepository.save(invoice);
    }
    
}
