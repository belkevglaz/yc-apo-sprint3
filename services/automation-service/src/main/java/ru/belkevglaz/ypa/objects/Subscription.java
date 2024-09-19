package ru.belkevglaz.ypa.objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 *  Сущность подписки на динамические топики в Kafka.
 *
 * @since 1.0
 */
@Data
@Entity
public class Subscription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Наименование топика для подписки. В качестве топика будет использоваться уникальный идентификатор дома.
	 */
	private String houseId;

}
