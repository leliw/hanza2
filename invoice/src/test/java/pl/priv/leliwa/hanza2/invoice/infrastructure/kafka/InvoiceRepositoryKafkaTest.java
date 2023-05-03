package pl.priv.leliwa.hanza2.invoice.infrastructure.kafka;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import pl.priv.leliwa.hanza2.invoice.domain.model.Invoice;

@SpringBootTest(properties="spring.application.repository=Kafka")
class InvoiceRepositoryKafkaTest {

    @Autowired
    private InvoiceRepositoryKafka invoiceRepositoryKafka;
    private Invoice invoice;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        invoice = Invoice.builder()
                .id(UUID.randomUUID())
                .netAmount(BigDecimal.valueOf(100L))
                .build();
    }

    @Test
    void findById_returnInvoiceDetails_whenFound() throws Exception {
        // Given
        //when(invoiceRepositoryKafka.findById(invoice.getId())).thenReturn(Optional.of(invoice));
        Invoice savedInvoice = invoiceRepositoryKafka.save(invoice);
        Thread.sleep(10000);
        // When
        Optional<Invoice> invoiceDetails = invoiceRepositoryKafka.findById(savedInvoice.getId());

        // Then
        assertEquals(Optional.of(invoice).toString(), invoiceDetails.toString());

        //verify(invoiceRepositoryKafka, times(1)).findById(invoice.getId());
    }

    @Test
    void save_returnInvoiceDetails() throws Exception {
        // Given
        //when(invoiceRepositoryKafka.save(invoice)).thenReturn(invoice);

        // When
        Invoice savedInvoice = invoiceRepositoryKafka.save(invoice);

        // Then
        assertEquals(savedInvoice, invoice);

        // verify(invoiceRepositoryKafka, times(1)).save(invoice);
    }
}
