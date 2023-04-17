package pl.priv.leliwa.hanza2.invoice.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

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
public class InvoiceItem {

    private final Integer no;
    private final UUID productId;
    @Builder.Default
    private BigDecimal netAmount = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal grossAmount = BigDecimal.ZERO;

}
