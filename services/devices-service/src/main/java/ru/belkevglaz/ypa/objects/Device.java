package ru.belkevglaz.ypa.objects;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		property = "type"
)
@JsonSubTypes({
		@JsonSubTypes.Type(value = Relay.class, name = "relay"),
		@JsonSubTypes.Type(value = Sensor.class, name = "sensor"),
		@JsonSubTypes.Type(value = Module.class, name = "module")
})
@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Device extends PanacheEntity {

	protected String name;

	protected String manufacturer;

	protected String serialNumber;

	/**
	 * По-хорошему надо Long, но пусть будет String чтобы удобнее было с топики именовать.
	 */
	protected String houseId;

	abstract String getType();
}
