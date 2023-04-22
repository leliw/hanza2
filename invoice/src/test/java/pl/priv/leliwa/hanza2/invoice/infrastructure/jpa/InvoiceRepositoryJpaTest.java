package pl.priv.leliwa.hanza2.invoice.infrastructure.jpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pl.priv.leliwa.hanza2.invoice.domain.model.Invoice;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class InvoiceRepositoryJpaTest {

    private InvoiceRepositoryJpa invoiceRepositoryJpa;

    @Mock
    private InvoiceCrudRepositoryJpa invoiceCrudRepositoryJpa;

    private Invoice invoice;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        invoiceRepositoryJpa = new InvoiceRepositoryJpa(invoiceCrudRepositoryJpa);
        invoice = Invoice.builder()
                .id(UUID.randomUUID())
                .netAmount(BigDecimal.valueOf(100L))
                .build();
    }

    @Test
    void findById_returnInvoiceDetails_whenFound() {
        // Given
        when(invoiceCrudRepositoryJpa.findById(invoice.getId())).thenReturn(Optional.of(invoice));

        // When
        Optional<Invoice> invoiceDetails = invoiceRepositoryJpa.findById(invoice.getId());

        // Then
        assertEquals(invoiceDetails, Optional.of(invoice));

        verify(invoiceCrudRepositoryJpa, times(1)).findById(invoice.getId());
    }

    @Test
    void save_returnInvoiceDetails() {
        // Given
        when(invoiceCrudRepositoryJpa.save(invoice)).thenReturn(invoice);

        // When
        Invoice savedInvoice = invoiceRepositoryJpa.save(invoice);

        // Then
        assertEquals(savedInvoice, invoice);

        verify(invoiceCrudRepositoryJpa, times(1)).save(invoice);
    }
}
