package ru.belkevglaz.ypa.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ru.belkevglaz.ypa.objects.Module;
import ru.belkevglaz.ypa.repository.ModuleRepository;

@ApplicationScoped
public class ModuleService extends DeviceService<Module> {

	@Inject
	ModuleRepository repository;

	@Override
	ModuleRepository getRepository() {
		return repository;
	}
}
