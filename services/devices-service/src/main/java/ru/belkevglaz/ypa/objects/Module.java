package ru.belkevglaz.ypa.objects;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Модуль сопряжения датчиков, которые не имеют доступа в интернет, с системой.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Module extends Device {

	/**
	 * Состояние модуля, если применимо.
	 */
	String state;

	@Override
	String getType() {
		return "module";
	}
}
