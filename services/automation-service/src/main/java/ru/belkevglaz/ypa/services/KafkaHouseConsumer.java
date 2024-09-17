package ru.belkevglaz.ypa.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import ru.belkevglaz.ypa.client.DeviceCommandExecClient;
import ru.belkevglaz.ypa.objects.Subscription;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Consumer;

/**
 * Runnable kafka consumer.
 *
 * @since 1.0
 */
@Slf4j
@Data
@Builder
public class KafkaHouseConsumer implements Runnable {

	static ObjectMapper objectMapper = new ObjectMapper();

	private Subscription subscription;

	private long timeout;

	private volatile boolean stopped;

	private Properties props;

	private Consumer<ConsumerRecord<String, String>> action;

	@Override
	public void run() {

		try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
			consumer.subscribe(List.of(subscription.getHouseId()));
			log.info("Created subscription to [{}]", subscription.getHouseId());
			while (!stopped) {

//				log.info("Waiting events... [{}]", subscription.getHouseId());

				ConsumerRecords<String, String> records = consumer.poll(timeout);
				records.forEach(record -> {
					action.accept(record);
				});
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}
}
