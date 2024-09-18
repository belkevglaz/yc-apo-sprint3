package ru.belkevglaz.ypa.kafka;

import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import ru.belkevglaz.ypa.objects.Telemetry;

import java.util.concurrent.CompletableFuture;

/**
 *
 */
@Slf4j
@ApplicationScoped
public class KafkaProducer {

	@Channel("dynamic-kafka")
	Emitter<Telemetry> emitter;

	public Telemetry send(String topic, Telemetry telemetry) {

		KafkaRecord<String, Telemetry> record = KafkaRecord.of(topic, null, telemetry);

		emitter.send(
				record
						.withAck(() -> {
							log.info("📧 Sent telemetry to kafka topic [{}]: {}", topic, telemetry);
							return CompletableFuture.completedFuture(null);
						})
		);

		return telemetry;
	}
}
