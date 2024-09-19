package ru.belkevglaz.ypa.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import ru.belkevglaz.ypa.exceptions.NotExistsException;
import ru.belkevglaz.ypa.objects.Module;
import ru.belkevglaz.ypa.objects.Sensor;
import ru.belkevglaz.ypa.repository.SensorRepository;

import java.util.List;

/**
 */
@ApplicationScoped
public class SensorService extends DeviceService<Sensor> {

	@Inject
	SensorRepository sensorRepository;

	@Inject
	ModuleService moduleService;

	@Transactional
	public Sensor linkSensorToModule(String sensorId, String moduleId) throws NotExistsException {
		Module module = moduleService.findByIdOrSerialNumber(moduleId);

		if (module != null) {
			Sensor sensor = findByIdOrSerialNumber(sensorId);
			if (sensor != null) {
				sensor.setModule(module);
				getRepository().persist(sensor);
				return sensor;
			} else {
				throw new NotExistsException("Sensor with id/sh [" + moduleId + "] not found.");
			}
		}
		throw new NotExistsException("Module with id/sh [" + moduleId + "] not found.");
	}

	public void update(Sensor sensor) {
		getRepository().persist(sensor);
	}

	public List<Sensor> getLinkedByModuleId(String moduleId) {
		Module module = moduleService.findByIdOrSerialNumber(moduleId);
		if (module != null) {
			return getRepository().getLinkedByModuleId(module.id);
		} else {
			return List.of();
		}
	}

	@Override
	SensorRepository getRepository() {
		return sensorRepository;
	}
}
