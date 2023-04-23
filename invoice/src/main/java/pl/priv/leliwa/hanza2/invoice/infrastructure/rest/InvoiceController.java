package pl.priv.leliwa.hanza2.invoice.infrastructure.rest;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import pl.priv.leliwa.hanza2.invoice.domain.exception.InvoiceNotFoundException;
import pl.priv.leliwa.hanza2.invoice.domain.model.Invoice;
import pl.priv.leliwa.hanza2.invoice.domain.port.InvoiceRepository;
import pl.priv.leliwa.hanza2.invoice.domain.usecase.CreateInvoiceUseCase;
import pl.priv.leliwa.hanza2.invoice.domain.usecase.ShowInvoiceUseCase;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @PostMapping
    public ResponseEntity<Invoice> create(@RequestBody Invoice invoice) throws Exception {
        CreateInvoiceUseCase useCase = new CreateInvoiceUseCase(invoiceRepository);
        Invoice savedInvoice = useCase.execute(invoice);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedInvoice.getId())
                .toUri();
        return ResponseEntity
                .created(location)
                .body(savedInvoice);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> show(@PathVariable UUID id) throws InvoiceNotFoundException {
        ShowInvoiceUseCase useCase = new ShowInvoiceUseCase(invoiceRepository);
        Optional<Invoice> invoice = useCase.execute(id);
        if (invoice.isPresent()) {
            return ResponseEntity.ok(invoice.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
