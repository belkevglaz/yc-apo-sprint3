package ru.belkevglaz.ypa.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import ru.belkevglaz.ypa.objects.Sensor;

import java.util.List;

/**
 * @since 1.0
 */
@ApplicationScoped
public class SensorRepository implements PanacheRepository<Sensor> {

	public List<Sensor> getLinkedByModuleId(Long moduleId) {
		return find("module.id", moduleId).list();
	}
}
