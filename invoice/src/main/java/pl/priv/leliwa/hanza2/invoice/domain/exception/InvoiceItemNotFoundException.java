package pl.priv.leliwa.hanza2.invoice.domain.exception;

import java.util.UUID;

public class InvoiceItemNotFoundException extends Exception {

    public InvoiceItemNotFoundException(UUID invoiceId, Integer no) {
    }

}
