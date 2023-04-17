package pl.priv.leliwa.hanza2.invoice.domain.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pl.priv.leliwa.hanza2.invoice.domain.exception.InvoiceItemNotFoundException;
import pl.priv.leliwa.hanza2.invoice.domain.exception.InvoiceNotFoundException;
import pl.priv.leliwa.hanza2.invoice.domain.model.Invoice;
import pl.priv.leliwa.hanza2.invoice.domain.model.InvoiceItem;
import pl.priv.leliwa.hanza2.invoice.domain.port.InvoiceRepository;

public class InvoiceUseCaseTest {

    private InvoiceRepository invoiceRepository;

    @BeforeEach
    void setUp() {
        invoiceRepository = mock(InvoiceRepository.class);
    }

    @Test
    void create() {
        LocalDate saleDate = LocalDate.of(2023, 4, 17);
        Invoice invoice = Invoice.builder()
                .invoiceId(null)
                .saleDate(saleDate)
                .build();
        CreateInvoiceUseCase createInvoiceUseCase = new CreateInvoiceUseCase(invoiceRepository);

        createInvoiceUseCase.execute(invoice);

        verify(invoiceRepository).save(
                argThat(i -> i.getSaleDate().equals(saleDate) && i.getInvoiceId() != null));
    }

    @Test
    void addItemToExistingInvoice() throws InvoiceNotFoundException {
        UUID invoiceId = UUID.randomUUID();
        Invoice invoice = Invoice.builder().build();
        InvoiceItem invoiceItem = InvoiceItem.builder()
                .no(1)
                .productId(UUID.randomUUID())
                .netAmount(BigDecimal.valueOf(100))
                .grossAmount(BigDecimal.valueOf(123))
                .build();
        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));
        AddInvoiceItemUseCase addInvoiceItemUseCase = new AddInvoiceItemUseCase(invoiceRepository);

        addInvoiceItemUseCase.execute(invoiceId, invoiceItem);

        verify(invoiceRepository).save(
                argThat(i -> i.getNetAmount().equals(BigDecimal.valueOf(100)) &&
                        i.getGrossAmount().equals(BigDecimal.valueOf(123))));
    }

    @Test
    void addItemToNotExistingInvoice() throws InvoiceNotFoundException {
        UUID invoiceId = UUID.randomUUID();
        InvoiceItem invoiceItem = new InvoiceItem(1, UUID.randomUUID());
        AddInvoiceItemUseCase addInvoiceItemUseCase = new AddInvoiceItemUseCase(invoiceRepository);

        assertThrows(InvoiceNotFoundException.class, () -> {
            addInvoiceItemUseCase.execute(invoiceId, invoiceItem);
        });
    }

    @Test
    void removeItemFromExistingInvoice() throws InvoiceNotFoundException, InvoiceItemNotFoundException {
        UUID invoiceId = UUID.randomUUID();
        Invoice invoice = Invoice.builder().build();
        InvoiceItem invoiceItem = InvoiceItem.builder()
                .no(1)
                .productId(UUID.randomUUID())
                .netAmount(BigDecimal.valueOf(100))
                .grossAmount(BigDecimal.valueOf(123))
                .build();
        invoice.add(invoiceItem);
        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));
        RemoveInvoiceItemUseCase removeInvoiceItemUseCase = new RemoveInvoiceItemUseCase(invoiceRepository);

        removeInvoiceItemUseCase.execute(invoiceId, 1);

        verify(invoiceRepository).save(
                argThat(i -> i.getNetAmount().equals(BigDecimal.valueOf(0)) &&
                        i.getGrossAmount().equals(BigDecimal.valueOf(0))));
    }

    @Test
    void removeNotExistingItemFromExistingInvoice() throws InvoiceNotFoundException {
        UUID invoiceId = UUID.randomUUID();
        Invoice invoice = Invoice.builder().build();
        InvoiceItem invoiceItem = InvoiceItem.builder()
                .no(1)
                .productId(UUID.randomUUID())
                .netAmount(BigDecimal.valueOf(100))
                .grossAmount(BigDecimal.valueOf(123))
                .build();
        invoice.add(invoiceItem);
        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));
        RemoveInvoiceItemUseCase removeInvoiceItemUseCase = new RemoveInvoiceItemUseCase(invoiceRepository);

        assertThrows(InvoiceItemNotFoundException.class, () -> {
            removeInvoiceItemUseCase.execute(invoiceId, 2);
        });
    }

    @Test
    void removeItemFromNotExistingInvoice() {
        UUID invoiceId = UUID.randomUUID();
        Invoice invoice = Invoice.builder().build();
        InvoiceItem invoiceItem = InvoiceItem.builder()
                .no(1)
                .productId(UUID.randomUUID())
                .netAmount(BigDecimal.valueOf(100))
                .grossAmount(BigDecimal.valueOf(123))
                .build();
        invoice.add(invoiceItem);
        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));
        RemoveInvoiceItemUseCase removeInvoiceItemUseCase = new RemoveInvoiceItemUseCase(invoiceRepository);

        assertThrows(InvoiceNotFoundException.class, () -> {
            removeInvoiceItemUseCase.execute(UUID.randomUUID(), 1);
        });
    }

    @Test
    void showExistingInvoice() throws InvoiceNotFoundException {
        InvoiceItem invoiceItem = InvoiceItem.builder()
                .no(1)
                .productId(UUID.randomUUID())
                .netAmount(BigDecimal.valueOf(100))
                .grossAmount(BigDecimal.valueOf(123))
                .build();
        Invoice invoice = Invoice.builder()
                .items(Arrays.asList(invoiceItem))
                .build();
        UUID invoiceId = invoice.getInvoiceId();
        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));
        ShowInvoiceUseCase showInvoiceUseCase = new ShowInvoiceUseCase(invoiceRepository);

        Optional<Invoice> actual = showInvoiceUseCase.execute(invoiceId);

        assertEquals(invoice, actual.get());
    }

    @Test
    void showNotExistingInvoice() throws InvoiceNotFoundException {
        UUID invoiceId = UUID.randomUUID();
        when(invoiceRepository.findById(any())).thenReturn(Optional.empty());
        ShowInvoiceUseCase showInvoiceUseCase = new ShowInvoiceUseCase(invoiceRepository);

        Optional<Invoice> invoice =  showInvoiceUseCase.execute(invoiceId);
        
        assertFalse(invoice.isPresent());
    }
}
