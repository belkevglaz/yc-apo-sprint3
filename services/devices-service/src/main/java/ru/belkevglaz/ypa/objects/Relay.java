package ru.belkevglaz.ypa.objects;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Реле.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Relay extends Device {

	/**
	 * Признак вкл/выкл.
	 */
	boolean isActivated = false;

	/**
	 * Текущее состояние реле.
	 */
	float currentValue;

	/**
	 * Целевое значение.
	 */
	float targetValue;


	@Override
	String getType() {
		return "relay";
	}
}