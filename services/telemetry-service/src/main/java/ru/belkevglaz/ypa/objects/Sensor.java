package ru.belkevglaz.ypa.objects;

import lombok.Data;

/**
 * Датчик.
 */
@Data
public class Sensor {

	private Long id;

	private String name;

	private String manufacturer;

	private String serialNumber;

	private Long houseId;

}