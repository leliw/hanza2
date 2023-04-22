package pl.priv.leliwa.hanza2.invoice.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
@ToString
@Entity
public class InvoiceItem {

    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();
    @ManyToOne
    @JoinColumn(name="invoice_id", nullable=false)
    private Invoice invoice;
    private final Integer no;
    private final UUID productId;
    @Builder.Default
    private BigDecimal netAmount = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal grossAmount = BigDecimal.ZERO;

}
