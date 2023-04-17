package pl.priv.leliwa.hanza2.invoice.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pl.priv.leliwa.hanza2.invoice.domain.exception.InvoiceItemNotFoundException;


@Builder(toBuilder = true)
@Getter
@ToString
public class Invoice {

    @Builder.Default
    private UUID invoiceId = UUID.randomUUID();
    @Builder.Default
    private Optional<String> number = Optional.empty();
    @Builder.Default
    private LocalDate saleDate = LocalDate.now();
    @Builder.Default
    private List<InvoiceItem> items = new ArrayList<>();
    @Builder.Default
    private BigDecimal netAmount = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal grossAmount = BigDecimal.ZERO;

    public void add(InvoiceItem invoiceItem) {
        this.items.add(invoiceItem);
        this.netAmount = this.netAmount.add(invoiceItem.getNetAmount());
        this.grossAmount = this.grossAmount.add(invoiceItem.getGrossAmount());
    }

    public void remove(Integer no) throws InvoiceItemNotFoundException {
        InvoiceItem invoiceItem = getItem(no).orElseThrow(() -> new InvoiceItemNotFoundException(this.invoiceId, no));
        this.items.remove(invoiceItem);
        this.netAmount = this.netAmount.subtract(invoiceItem.getNetAmount());
        this.grossAmount = this.grossAmount.subtract(invoiceItem.getGrossAmount());
    }

    private Optional<InvoiceItem> getItem(Integer no) {
        return this.items.stream().filter(i -> i.getNo().equals(no)).findFirst();
    }

}
