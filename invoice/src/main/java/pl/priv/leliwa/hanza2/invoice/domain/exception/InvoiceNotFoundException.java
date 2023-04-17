package pl.priv.leliwa.hanza2.invoice.domain.exception;

import java.util.UUID;

public class InvoiceNotFoundException extends Exception {

    public InvoiceNotFoundException(UUID invoiceId) {
    }

}
