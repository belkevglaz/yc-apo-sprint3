package ru.belkevglaz.ypa.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import io.quarkus.runtime.Shutdown;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import ru.belkevglaz.ypa.client.DeviceCommandExecClient;
import ru.belkevglaz.ypa.objects.Subscription;
import ru.belkevglaz.ypa.repository.SubscriptionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Сервис для работы с подписками. Создание, запуск, остановка и т.п.
 *
 * @since 1.0
 */
@Slf4j
@ApplicationScoped
public class SubscriptionService {

	@ConfigProperty(name = "kafka.bootstrap.servers")
	String kafkaBootstrapServers;

	@Inject
	SubscriptionRepository subscriptionRepository;

	@RestClient
	DeviceCommandExecClient deviceCommandExecClient;

	private List<KafkaHouseConsumer> consumers = new ArrayList<>();

	@Startup
	public void init() {
		List<Subscription> subscriptions = subscriptionRepository.findAll().stream().toList();

		subscriptions.forEach(s -> {
			// создаем и запускаем сохранённые подписки
			val consumer = createConsumer(s, 1000);
			consumers.add(runConsumer(consumer));
		});
	}


	/**
	 * Создаем и запускаем подписку. Каждая подписка - события по конкретному дому
	 *
	 * @param subscription {@link Subscription}
	 * @return {@link Subscription}
	 */
	@Transactional
	public Subscription createSubscription(Subscription subscription) {

		// todo : check if not exists
		subscriptionRepository.persist(subscription);

		val consumer = createConsumer(subscription, 1000);
		consumers.add(runConsumer(consumer));

		return subscription;
	}

	/**
	 * Создаём экземпляр нашего Kafka Consumer-а.
	 * @param s {@link Subscription}
	 * @param timeout таймаут пуллинга
	 * @return {@link KafkaHouseConsumer}
	 */
	protected KafkaHouseConsumer createConsumer(Subscription s, long timeout) {
		return KafkaHouseConsumer.builder()
				.subscription(s)
				.props( getKafkaProps())
				.timeout(timeout)
				.action(cr -> {
					log.info("🎉 Consume event:  House {}, event {}", s.getHouseId(), cr.value());
					log.info("✅ Check device on device service. It's OK");
					log.info("⏯ Launch scenario....");
					log.info("🆗 Exec command.");

					ObjectReader reader = new ObjectMapper().readerFor(Map.class);

					Map<String, String> map;
					try {
						map = reader.readValue(cr.value());
						val command = new DeviceCommandExecClient.Command();
						command.setCommand("SetTargetTemperature = 26.5");
						command.setDeviceId(map.get("deviceId"));

						deviceCommandExecClient.exec(command);

					} catch (JsonProcessingException e) {
						log.error(e.getMessage(), e);
					}

				})
				.build();
	}


	public KafkaHouseConsumer runConsumer(KafkaHouseConsumer consumer) {
		// Запускаем consumer в отдельном потоке
		Thread consumerThread = new Thread(consumer);
		consumerThread.start();

		// Добавляем shutdown hook для корректной остановки
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			consumer.setStopped(true);
			try {
				consumerThread.join(); // Ожидаем завершения потока
			} catch (InterruptedException e) {
				log.error(e.getMessage(), e);
			}
		}));

		return consumer;
	}

	public Properties getKafkaProps() {
		Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		return props;
	}

	@Shutdown
	public void shutdown() {
		consumers.forEach(c -> c.setStopped(true));
	}
}
