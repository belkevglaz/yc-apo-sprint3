package ru.belkevglaz.ypa.services;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ru.belkevglaz.ypa.objects.Relay;
import ru.belkevglaz.ypa.repository.RelayRepository;

/**
 * @since 1.0
 */
@ApplicationScoped
public class RelayService extends DeviceService<Relay> {

	@Inject
	RelayRepository repository;

	@Override
	PanacheRepository<Relay> getRepository() {
		return repository;
	}
}
