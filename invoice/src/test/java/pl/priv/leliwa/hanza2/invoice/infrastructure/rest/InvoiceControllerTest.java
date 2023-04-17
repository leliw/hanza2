package pl.priv.leliwa.hanza2.invoice.infrastructure.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import pl.priv.leliwa.hanza2.invoice.domain.model.Invoice;
import pl.priv.leliwa.hanza2.invoice.domain.port.InvoiceRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class InvoiceControllerTest {

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private InvoiceRepository invoiceRepository;

    @Test
    public void showNotExistingInvoice() throws Exception {
        Invoice invoice = Invoice.builder()
                .build();
        when(invoiceRepository.findById(invoice.getInvoiceId())).thenReturn(Optional.of(invoice));

        assertThat(
                this.restTemplate
                        .withBasicAuth("user", "pass")
                        .getForEntity("http://localhost:" + port + "/api/invoices/" + invoice.getInvoiceId(), Invoice.class)
                        .getStatusCode())
                .isEqualTo(HttpStatus.OK);
    }

    @Test
	public void showExistingInvoice() throws Exception {
        when(invoiceRepository.findById(any())).thenReturn(Optional.empty());

		assertThat(
            this.restTemplate
            .withBasicAuth("user", "pass")
            .getForEntity("http://localhost:" + port + "/api/invoices/" + UUID.randomUUID(), Invoice.class)
            .getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

    @Test
    public void createInvoice() throws Exception {
        Invoice invoice = Invoice.builder().build();

        assertThat(
                this.restTemplate
                        .withBasicAuth("user", "pass")
                        .postForLocation("http://localhost:" + port + "/api/invoices", invoice))
                .hasFragment(null);
    }

}
