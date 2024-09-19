package ru.belkevglaz.ypa.objects;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * Датчик.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NamedQueries({
		@NamedQuery(name = "Sensor.getLinkedSensors", query = "from Sensor where module = ?1"),
})
@RegisterForReflection
public class Sensor extends Device {

	/**
	 * Значение датчика.
	 */
	float value;

	/**
	 * Сетевой адрес датчика, работающего напрямую через интернет.
	 */
	String directAddress;

	/**
	 * Дата.Время последнего обновления.
	 */
	LocalDateTime lastUpdate;

	/**
	 * Модуль, который управляет данным датчиком, если применимо.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "module_id")
	private Module module;

	@Override
	String getType() {
		return "sensor";
	}
}