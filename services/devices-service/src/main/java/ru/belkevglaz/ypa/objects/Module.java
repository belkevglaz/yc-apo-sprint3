package ru.belkevglaz.ypa.objects;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

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
