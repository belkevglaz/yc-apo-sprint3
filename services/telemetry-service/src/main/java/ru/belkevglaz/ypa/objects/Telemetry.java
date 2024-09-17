package ru.belkevglaz.ypa.objects;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * Класс данных телеметрии.
 */
@Data
@Builder
public class Telemetry {

	/**
	 * Идентификатор устройства или его серийный номер.
	 */
	String deviceId;

	/**
	 * Идентификатор модуля или его серийный номер.
	 */
	String moduleId;

	/**
	 * Идентификатор дома.
	 */
	String houseId;

	/**
	 * Дата события.
	 */
	Date createdAt;

	/**
	 * Исходные данные с датчика.
	 */
	String payload;

	/**
	 * Значение, переданное с датчика. Вытаскиваем его из {@link #payload} и храним отдельно чтобы было удобнее потом с ним работать.
	 */
	String value;

}