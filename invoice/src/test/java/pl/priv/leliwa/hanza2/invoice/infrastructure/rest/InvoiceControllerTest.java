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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import pl.priv.leliwa.hanza2.invoice.domain.model.Invoice;
import pl.priv.leliwa.hanza2.invoice.domain.port.InvoiceRepository;

@SpringBootTest(properties="spring.application.repository=JPA", webEnvironment = WebEnvironment.RANDOM_PORT)
public class InvoiceControllerTest {

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private InvoiceRepository invoiceRepository;

    @Test
    public void showExistingInvoice() throws Exception {
        Invoice invoice = Invoice.builder()
                .build();
        when(invoiceRepository.findById(invoice.getId())).thenReturn(Optional.of(invoice));

        assertThat(
                this.restTemplate
                        .getForEntity("http://localhost:" + port + "/api/invoices/"
                                + invoice.getId(), Invoice.class)
                        .getStatusCode())
                .isEqualTo(HttpStatus.OK);
    }

    @Test
        public void showNotExistingInvoice() throws Exception {
                when(invoiceRepository.findById(any())).thenReturn(Optional.empty());

		assertThat(
            this.restTemplate
                .getForEntity("/api/invoices/" + UUID.randomUUID(), Invoice.class)
                .getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
        }

    @Test
    public void createInvoice() throws Exception {
        Invoice invoice = Invoice.builder().build();

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("user", "pass");
        HttpEntity<Invoice> request = new HttpEntity<>(invoice, headers);

        ResponseEntity<Invoice> response = this.restTemplate
                .exchange("/api/invoices", HttpMethod.POST, request, Invoice.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getLocation()).isNotNull();
    }

}
