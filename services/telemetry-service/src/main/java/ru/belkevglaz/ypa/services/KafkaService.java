package ru.belkevglaz.ypa.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import ru.belkevglaz.ypa.kafka.KafkaProducer;
import ru.belkevglaz.ypa.objects.Telemetry;

/**
 *
 */
@Slf4j
@ApplicationScoped
public class KafkaService {

	@Inject
	KafkaProducer producer;

//	@Inject
//	DeviceClient deviceClient;

	/**
	 * Публикуем данные.
	 *
	 * @param raw сырые данные с датчика
	 */
	public Telemetry publishRawTelemetry(String raw) {
		val telemetry = transformTelemetry(raw);
		return publishTelemetry(telemetry);
	}

	/**
	 * Публикуем данные телеметрии.
	 *
	 * @param telemetry {@link Telemetry}
	 * @return {@link Telemetry}
	 */
	public Telemetry publishTelemetry(Telemetry telemetry) {

		telemetry.setHouseId("Settl-1-House-1");

		return producer.send(telemetry.getHouseId(), telemetry);
	}


	/**
	 * Конвертируем сырые данные в {@link Telemetry}.
	 *
	 * @param raw сырые данные
	 * @return {@link Telemetry}
	 */
	Telemetry transformTelemetry(String raw) {

		// todo : implements transform raw data to Telemetry object
		log.info("Transformed raw data to telemetry");
		return Telemetry.builder().deviceId("DEV-NUM-01").build();

	}

}
