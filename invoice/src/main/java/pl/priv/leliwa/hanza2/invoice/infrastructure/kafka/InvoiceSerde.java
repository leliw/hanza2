package pl.priv.leliwa.hanza2.invoice.infrastructure.kafka;

import org.apache.kafka.common.serialization.Serdes.WrapperSerde;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import pl.priv.leliwa.hanza2.invoice.domain.model.Invoice;

public class InvoiceSerde extends WrapperSerde<Invoice> {

    public InvoiceSerde() {
        super(new JsonSerializer<>(), new JsonDeserializer<>(Invoice.class));
    }

}
