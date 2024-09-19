package ru.belkevglaz.ypa.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import ru.belkevglaz.ypa.kafka.KafkaProducer;

/**
 *
 */
@Slf4j
@ApplicationScoped
public class TelemetryService {

	@Inject
	KafkaProducer producer;

//	@Inject
//	DeviceClient deviceClient;

	/**
	 * Публикуем данные.
	 *
	 * @param raw сырые данные с датчика
	 */
	public ru.belkevglaz.ypa.objects.Telemetry publishRawTelemetry(String raw) {
		val telemetry = transformTelemetry(raw);
		return publishTelemetry(telemetry);
	}

	/**
	 * Публикуем данные телеметрии.
	 *
	 * @param telemetry {@link ru.belkevglaz.ypa.objects.Telemetry}
	 * @return {@link ru.belkevglaz.ypa.objects.Telemetry}
	 */
	public ru.belkevglaz.ypa.objects.Telemetry publishTelemetry(ru.belkevglaz.ypa.objects.Telemetry telemetry) {

		telemetry.setHouseId("Settl-1-House-1");

		return producer.send(telemetry.getHouseId(), telemetry);
	}


	/**
	 * Конвертируем сырые данные в {@link ru.belkevglaz.ypa.objects.Telemetry}.
	 *
	 * @param raw сырые данные
	 * @return {@link ru.belkevglaz.ypa.objects.Telemetry}
	 */
	ru.belkevglaz.ypa.objects.Telemetry transformTelemetry(String raw) {

		// todo : implements transform raw data to Telemetry object
		log.info("Transformed raw data to telemetry");
		return ru.belkevglaz.ypa.objects.Telemetry.builder().deviceId("DEV-NUM-01").build();

	}

}
