package pl.priv.leliwa.hanza2.invoice.infrastructure.kafka;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KafkaStreams.State;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

import lombok.RequiredArgsConstructor;
import pl.priv.leliwa.hanza2.invoice.domain.model.Invoice;
import pl.priv.leliwa.hanza2.invoice.domain.port.InvoiceRepository;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "spring.application.repository", havingValue = "Kafka")
public class InvoiceRepositoryKafka implements InvoiceRepository {

    final StreamsBuilderFactoryBean streamsBuilderFactoryBean;
    final KafkaTemplate<UUID, Invoice> kafkaTemplate;

    private final String topic = "invoice";
    private final String table = "table-" + topic;

    final Serde<UUID> uuidSerde = Serdes.UUID();
    final Serde<Invoice> invoiceSerde = new InvoiceSerde();

    @Override
    public Optional<Invoice> findById(UUID invoiceId) throws InterruptedException {
        final KafkaStreams kafkaStreams = streamsBuilderFactoryBean.getKafkaStreams();
        while (kafkaStreams.state() != State.RUNNING)
            Thread.sleep(100);
        ReadOnlyKeyValueStore<UUID, Invoice> invoices = kafkaStreams.store(
                StoreQueryParameters.fromNameAndType(table, QueryableStoreTypes.keyValueStore()));
        return Optional.ofNullable(invoices.get(invoiceId));
    }

    @Override
    public Invoice save(Invoice invoice) throws Exception {
        kafkaTemplate.send(topic, invoice.getId(), invoice).get(10, TimeUnit.SECONDS);
        return invoice;
    }

    @Bean
    NewTopic topicInvoice() {
        return TopicBuilder.name(topic)
                .partitions(10)
                .replicas(1)
                .build();
    }
    
    @Bean
    KTable<UUID, Invoice> kTable(StreamsBuilder streamsBuilder) {
        KStream<UUID, Invoice> kStream = streamsBuilder.stream(topic);
        return kStream.toTable(Materialized.as(table));
    }

}
